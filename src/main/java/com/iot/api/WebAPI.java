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
		// Trả về jwt cho người dùng.
		String jwt = tokenProvider.generateJwtTokenUsername(authentication);
		return new JwtAuthSigninResponse(loginRequest.getUsername(), jwt);
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

	// Lấy danh sách user
	@GetMapping("/api/auth/list")
	public List<UserDto> getAllUser() {
		return userService.findAll();
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

	// Thêm mới thiết bị
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
				result.setDeviceId(device.getId());
				result.setToken(user_token);
			}
		}
		/*
		 * result đã có đủ cả sensor list nhé
		 */
		return result;
	}

	// Generate token device authen(Active device)
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

	// Generate token device authen(Active device)
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

	@GetMapping("/api/device/{id}/lastdata")
	public List<SensorDataDto> getLastDataSensor(@PathVariable("id") Long id) {
		List<SensorDataDto> result = sensorDataService.findAllDataLastSensorId(id);
		return result;
	}
	/*
	 * @GetMapping("/api/device/{id}/alldata") public List<SensorDataDto>
	 * getAllDataSensor(@PathVariable("id") Long id) { List<SensorDataDto> result =
	 * sensorDataService.findAllDataSensorId(id); return result; }
	 */

	@GetMapping("/api/device/{id}/alldata")
	public List<SensorDto> getAllDataSensor(@PathVariable("id") Long id) {
		List<SensorDto> result = sensorService.getAllData(id);
		return result;
	}

	@GetMapping("/api/device/{id}/listsensor")
	public List<SensorDto> getListSensorOfDevice(@PathVariable("id") Long id) {
		List<SensorDto> result = new ArrayList<SensorDto>(deviceService.getListSensor(id).getSensorList());

		return result;
	}

}
