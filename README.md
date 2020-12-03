# SpringIOT

- Các thư viện liên quan:
    - SpringMVC 4, Spring security
    - jdk-8
    - tomcat 8.5
    - MySQL 8.0.22
    - eclipseIDE
    - maven 3.6.3

- Khi clone project về chạy terminal>mvn clean install và update lại maven
- Luồng chạy: 
    + user đăng ký tài khoản username,password ->user đăng nhập ->Hệ thống trả về jwt(user_token là username)
    + user thêm mới device(đồng thời insert các sensor liên quan đến device) =>Trả về jwt(deviceID và jwt(username))
    + Người dùng nạp code copy jwt(deviceID và user_token(username)) vào device thật và publish authentication server sẽ xác nhận và kích hoạt device(alive=1) sau đó trả về json(deviceID và device_token(userID,deviceID))
    + Thiết bị authen thành công và nhận được json(deviceID và device_token(userID,deviceID)) sẽ sử dụng nó để publish dữ liệu gửi lên server.

- Để khắc phục lỗi:"Could not write JSON: could not initialize proxy - no Session; nested exception is com.fasterxml.jackson.databind.JsonMappingException: could not initialize proxy - no Session"   Xẩy ra khi ta để fetch.LAZY ở prop trong class. Điều này xẩy ra là do JSON cố load LAZY nhưng nó đã kết thúc "trans". Do đó có vài cách khắc phục như thêm từ khóa @JsonIgnore ở prop có LAZY hoặc ta sẽ sử dụng "JOIN FETCH" để lấy cả dữ liệu của nó lên. Nhưng có cách đơn giản hơn là ta sẽ sử dụng 1 lớp nữa là DTO. trong lớp này ta sẽ bắt try{ những cái mà set, get LAZY) =>nó sẽ xẩy ra lỗi "could not initialize proxy - no Session" nhưng do bắt try rồi nên nó vẫn vượt qua được và lúc JSON gọi thì trả ở DTO (thay vì entity)
