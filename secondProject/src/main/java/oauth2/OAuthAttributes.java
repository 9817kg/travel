package second.project.oauth2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {

    GOOGLE("google", (attribute) -> {
	// 현재 날짜와 시간을 가져옵니다.
	LocalDateTime currentDateTime = LocalDateTime.now();

	// 날짜와 시간을 원하는 형식으로 포맷합니다.
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String formattedDateTime = currentDateTime.format(formatter);

	UserProfile userProfile = new UserProfile();
	userProfile.setJoinDate(formattedDateTime);
	userProfile.setUserName((String) attribute.get("name"));
	userProfile.setEmail((String) attribute.get("email"));
	userProfile.setProfile("profile.jpg");
	return userProfile;
    }),

    NAVER("naver", (attribute) -> {
	UserProfile userProfile = new UserProfile();

	Map<String, String> responseValue = (Map) attribute.get("response");
	// 현재 날짜와 시간을 가져옵니다.
	LocalDateTime currentDateTime = LocalDateTime.now();

	// 날짜와 시간을 원하는 형식으로 포맷합니다.
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String formattedDateTime = currentDateTime.format(formatter);
	userProfile.setJoinDate(formattedDateTime);
	userProfile.setUserName(responseValue.get("name"));
	userProfile.setEmail(responseValue.get("email"));
	String birthday = (String) responseValue.get("birthday");
	String birthyear = (String) responseValue.get("birthyear");
	System.err.println(birthday + "   " + birthyear);

	userProfile.setBirth(birthyear + "-" + birthday);

	String rawPhoneNumber = (String) responseValue.get("mobile"); // 예: "+82-5568-9801"
	userProfile.setMobile(rawPhoneNumber);
	userProfile.setProfile("profile.jpg");

	return userProfile;
    }),

    KAKAO("kakao", (attribute) -> {

	Map<String, Object> account = (Map<String, Object>) attribute.get("kakao_account");
	Map<String, String> profile = (Map<String, String>) account.get("profile");
	String email = (String) account.get("email");

	// 현재 날짜와 시간을 가져옵니다.
	LocalDateTime currentDateTime = LocalDateTime.now();

	// 날짜와 시간을 원하는 형식으로 포맷합니다.
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String formattedDateTime = currentDateTime.format(formatter);

	UserProfile userProfile = new UserProfile();
	userProfile.setJoinDate(formattedDateTime);
	userProfile.setUserName(profile.get("nickname"));
	userProfile.setEmail(email);
	String birthday = (String) account.get("birthday");
	String birthyear = (String) account.get("birthyear");
	System.err.println(birthday + "   " + birthyear);
	userProfile.setProfile("profile.jpg");
	String formattedBirthday = birthday.substring(0, 2) + "-" + birthday.substring(2);
	userProfile.setBirth(birthyear + "-" + formattedBirthday);

	String rawPhoneNumber = (String) account.get("phone_number"); // 예: "+82-5568-9801"

	// "+" 기호를 제거하고 "-"로 분리
	String[] phoneNumberParts = rawPhoneNumber.replace("+82", "").split("-");
	String countryCode = phoneNumberParts[0];
	String areaCode = phoneNumberParts[1];

	// 전화번호 부분을 "-"로 조합
	String[] remainingNumberParts = Arrays.copyOfRange(phoneNumberParts, 2, phoneNumberParts.length);
	String remainingNumber = String.join("-", remainingNumberParts);

	// 최종 전화번호 형식으로 조합
	String formattedPhoneNumber = "010" + "-" + areaCode + "-" + remainingNumber;

	userProfile.setMobile(formattedPhoneNumber);

	return userProfile;

    });

    private final String registrationId; // 로그인한 서비스(ex) google, naver..)
    private final Function<Map<String, Object>, UserProfile> of; // 로그인한 사용자의 정보를 통하여 UserProfile을 가져옴

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfile> of) {
	this.registrationId = registrationId;
	this.of = of;
    }

    public static UserProfile extract(String registrationId, Map<String, Object> attributes) {
	return Arrays.stream(values()).filter(value -> registrationId.equals(value.registrationId)).findFirst()
		.orElseThrow(IllegalArgumentException::new).of.apply(attributes);
    }
}
