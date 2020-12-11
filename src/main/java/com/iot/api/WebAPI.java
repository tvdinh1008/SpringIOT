package com.iot.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iot.authentication.JwtTokenProvider;
import com.iot.authentication.MyUser;
import com.iot.dto.DeviceDto;
import com.iot.dto.RoleDto;
import com.iot.dto.SensorDataDto;
import com.iot.dto.SensorDto;
import com.iot.dto.UserDto;
import com.iot.payloads.JwtAuthRequest;
import com.iot.payloads.JwtAuthSigninResponse;
import com.iot.service.IDeviceService;
import com.iot.service.IRoleService;
import com.iot.service.ISensorDataService;
import com.iot.service.ISensorService;
import com.iot.service.IUserService;

@RestController(value = "homeApiControllerOfWeb")
//@CrossOrigin
@CrossOrigin(origins = "*")
public class WebAPI {
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IDeviceService deviceService;
	@Autowired
	private ISensorDataService sensorDataService;
	@Autowired
	private ISensorService sensorService;

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping(value = "/api/role")
	private String saveRole(@RequestBody RoleDto role) {
		RoleDto result = roleService.save(role);
		if (result != null) {
			return "Save role success";
		}
		return "Save role false";
	}

	/*
	 * Đăng ký
	 */
	@PostMapping(value = "/api/auth/signup")
	private String signUp(@RequestBody UserDto user) {
		UserDto result = userService.save(user);
		if (result != null) {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.generateJwtTokenUsername(authentication);
			String token = "token: " + jwt;
			return token;
		}
		return "Save user false";
	}

	/*
	 * Đăng nhập
	 */
	@PostMapping("/api/auth/signin")
	public JwtAuthSigninResponse signin(@RequestBody UserDto loginRequest) {
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			// Nếu không xảy ra exception tức là thông tin hợp lệ
			// Set thông tin authentication vào Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		MyUser userPrincipal = (MyUser) authentication.getPrincipal();
		// Trả về jwt cho người dùng.
		String jwt = tokenProvider.generateJwtTokenUsername(authentication);
		return new JwtAuthSigninResponse(userPrincipal, jwt);
	}

	// cập nhật user
	@PutMapping("/api/auth")
	public UserDto updateUser(@RequestBody UserDto user) {
		UserDto result = userService.save(user);
		return result;
	}

	// thêm mới user
	@PostMapping(value = "/api/auth")
	private UserDto register(@RequestBody UserDto user) {
		UserDto result = userService.save(user);
		return result;
	}

	// thông tin user
	@GetMapping("/api/auth/{id}")
	public UserDto getProfile(@PathVariable("id") Long id) {
		return userService.findById(id);
	}

	// xóa user
	@DeleteMapping("/api/auth")
	public Boolean deleteUser(@RequestBody Long[] ids) {
		System.out.println("Delete ok " + ids[0]);
		return null;
	}

	// Generate TokenUser
	@PostMapping("/api/auth/token")
	public String getTokenUser(@RequestBody UserDto dto) {
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// Trả về jwt cho người dùng.
		String jwt = "token: " + tokenProvider.generateJwtTokenUsername(authentication);

		return jwt;
	}

	/*
	 * Lấy danh sách device ứng với user dựa vào jwt của user
	 */
	@GetMapping("/api/device/list")
	public List<DeviceDto> getListDeviceByUser(HttpServletRequest request) {
		List<DeviceDto> result = new ArrayList<DeviceDto>();
		String user_token = "";
		String token = "";
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(token)) {
				user_token = tokenProvider.getUserNameFromJwtToken(token);
				result = deviceService.getListDeviceByUser(user_token);
			}
		}

		return result;
	}

	/*
	 * Thêm mới thiết bị user tự thêm dựa vào jwt của user
	 */
	@PostMapping("/api/device")
	public JwtAuthRequest saveDevice(@RequestBody DeviceDto device, HttpServletRequest request) {
		String user_token = "";
		JwtAuthRequest result = new JwtAuthRequest();
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto user = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				device.setUserDto(user);
				device = deviceService.save(device);
				result.setDeviceDto(device);
				result.setDeviceId(device.getId());
				result.setToken(user_token);
			}
		}

		/*
		 * result đã có đủ cả sensor list nhé
		 */
		return result;
	}

	/*
	 * Cập nhật thiết bị dựa vào jwt của user
	 */
	@PutMapping("/api/device")
	public DeviceDto upDateDevice(@RequestBody DeviceDto device, HttpServletRequest request) {
		DeviceDto result = null;
		String user_token = "";
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto user = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				device.setUserDto(user);
				result = deviceService.save(device);
				result.setUserDto(user);
			}
		}
		// result đã có đủ cả sensor list nhé
		return result;
	}

	/*
	 * Lấy tất cả thông tin của thiết bị dựa vào jwt user
	 * 
	 */
	@GetMapping("/api/device/{id}")
	public DeviceDto getInfoDevice(@PathVariable("id") Long id, HttpServletRequest request) {
		DeviceDto result = null;
		String user_token = "";
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto user = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				result = deviceService.getInfoDevice(id, user.getUsername());
				result.setUserDto(user);
			}
		}
		return result;
	}

	/*
	 * Generate token device authen(Active device) dựa vào jwt user
	 */
	@GetMapping("/api/device/{id}/generatetoken")
	public JwtAuthRequest getGenerateAuthDevice(@PathVariable("id") Long id, HttpServletRequest request) {
		String user_token = "";
		DeviceDto deviceDto = deviceService.findById(id);
		JwtAuthRequest result = new JwtAuthRequest();
		String bearerToken = request.getHeader("Authorization");
		if (deviceDto != null && StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto user = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				result.setDeviceId(deviceDto.getId());

				result.setToken(tokenProvider.generateTokenAuthActiveDevice(user.getUsername(), deviceDto.getId()));
			}
		}
		return result;
	}

	/*
	 * Generate token device collect data dựa vào jwt user
	 */
	@GetMapping("/api/device/{id}/generatetokencollect")
	public JwtAuthRequest getGenerateTokenCollect(@PathVariable("id") Long id, HttpServletRequest request) {
		String user_token = "";
		DeviceDto deviceDto = deviceService.findById(id);
		JwtAuthRequest result = new JwtAuthRequest();
		String bearerToken = request.getHeader("Authorization");
		if (deviceDto != null && StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto user = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				result.setDeviceId(deviceDto.getId());

				result.setToken(tokenProvider.generateTokenCollectData(user.getId(), deviceDto.getId()));
			}
		}
		return result;
	}

	/*
	 * Lấy dữ liệu cập nhật lần cuối
	 */
	@GetMapping("/api/device/{id}/lastdata")
	public List<SensorDataDto> getLastDataSensor(@PathVariable("id") Long id) {
		List<SensorDataDto> result = sensorDataService.findAllDataLastSensorId(id);
		return result;
	}

	/*
	 * Lấy tất cả dữ liệu sensor có status=1 liên quan đến device
	 */
	@GetMapping("/api/device/{id}/alldata")
	public List<SensorDto> getAllDataSensor(@PathVariable("id") Long id) {
		List<SensorDto> result = sensorService.getAllData(id);
		return result;
	}

	/*
	 * Lấy list sensor đang có status=1 liên quan đến device
	 */
	@GetMapping("/api/device/{id}/listsensor")
	public List<SensorDto> getListSensorOfDevice(@PathVariable("id") Long id) {
		List<SensorDto> result = new ArrayList<SensorDto>(deviceService.getListSensor(id).getSensorList());
		return result;
	}

	/*
	 * Phần liên quan đến admin
	 */

	/*
	 * Lấy danh sách user
	 */
	@GetMapping("/api/auth/list")
	public List<UserDto> getAllUser(HttpServletRequest request) {
		List<UserDto> result = new ArrayList<UserDto>();
		String user_token = "";
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto admin = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				if (admin.getRoleDto().getCode().equals("ADMIN")) {
					result = userService.findAll();
				}
			}
		}
		return result;
	}

	/*
	 * Lấy tất cả danh sách device
	 */
	@GetMapping("/api/admin/device/list")
	public List<DeviceDto> getListDeviceByAdmin(HttpServletRequest request) {
		List<DeviceDto> result = new ArrayList<DeviceDto>();
		String user_token = "";
		String token = "";
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(token)) {
				user_token = tokenProvider.getUserNameFromJwtToken(token);

				result = deviceService.getListDeviceByAdmin(user_token);

			}
		}

		return result;
	}

	/*
	 * Thêm mới thiết bị admin thêm
	 */
	@PostMapping("/api/admin/device")
	public JwtAuthRequest saveDeviceByAdmin(@RequestBody DeviceDto device, HttpServletRequest request) {
		String user_token = "";
		JwtAuthRequest result = new JwtAuthRequest();
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto admin = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				if (admin.getRoleDto().getCode().equals("ADMIN")) {
					UserDto user = userService.getUserWithUsername(device.getUserDto().getUsername());
					device.setUserDto(user);
					device = deviceService.save(device);
					result.setDeviceDto(device);
					result.setDeviceId(device.getId());
					result.setToken(user_token);
				}
			}
		}
		return result;
	}

	/*
	 * Lấy thông danh sách device ứng với 1 user dựa vào jwt admin
	 */
	@GetMapping("/api/admin/{username}/device/list")
	public List<DeviceDto> getListDeviceUserByAdmin(@PathVariable("username") String username,
			HttpServletRequest request) {
		List<DeviceDto> result = new ArrayList<DeviceDto>();
		@SuppressWarnings("unused")
		String user_token = "";
		String token = "";
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(token)) {
				user_token = tokenProvider.getUserNameFromJwtToken(token);
				result = deviceService.getListDeviceByUser(username);
			}
		}

		return result;
	}

	/*
	 * Lấy tất cả thông tin của thiết bị dựa vào jwt admin
	 * 
	 */
	@GetMapping("/api/admin/{username}/device/{id}")
	public DeviceDto getInfoDeviceUserByAdmin(@PathVariable("id") Long id, @PathVariable("username") String username,
			HttpServletRequest request) {
		DeviceDto result = null;
		String user_token = "";
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto admin = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				if (admin != null && admin.getRoleDto().getCode().equals("ADMIN")) {
					UserDto user = userService.getUserWithUsername(username);
					result = deviceService.getInfoDevice(id, user.getUsername());
					result.setUserDto(user);
				}
			}
		}
		return result;
	}

	/*
	 * Cập nhật thiết bị dựa vào jwt của admin
	 */
	@PutMapping("/api/admin/{username}/device")
	public DeviceDto upDateDevice(@RequestBody DeviceDto device, @PathVariable("username") String username,
			HttpServletRequest request) {
		DeviceDto result = null;
		String user_token = "";
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			user_token = bearerToken.substring(7);
			if (tokenProvider.validateJwtToken(user_token)) {
				UserDto admin = userService.getUserWithUsername(tokenProvider.getUserNameFromJwtToken(user_token));
				if (admin != null && admin.getRoleDto().getCode().equals("ADMIN")) {
					UserDto user = userService.getUserWithUsername(username);
					device.setUserDto(user);
					result = deviceService.save(device);
					result.setUserDto(user);
				}
			}
		}
		// result đã có đủ cả sensor list nhé
		return result;
	}
}
