# SpringIOT

SpringMVC 4, Spring security
jdk-8
tomcat 8.5
MySQL 8.0.22
eclipseIDE
maven 3.6.3

Khi clone project về chạy terminal>mvn clean install và update lại maven

Luồng chạy: 
    + user đăng ký tài khoản username,password ->user đăng nhập ->Hệ thống trả về jwt(user_token là username)
    + user thêm mới device(đồng thời insert các sensor liên quan đến device) =>Trả về jwt(deviceID và jwt(username))
    + Người dùng nạp code copy jwt(deviceID và user_token(username)) vào device thật và publish authentication server sẽ xác nhận và kích hoạt device(alive=1) sau đó trả về json(deviceID và device_token(userID,deviceID))
    + Thiết bị authen thành công và nhận được json(deviceID và device_token(userID,deviceID)) sẽ sử dụng nó để publish dữ liệu gửi lên server.
