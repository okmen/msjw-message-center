package cn.message.model;

import java.util.List;

public class UserCarInfo{
	private String code;
	private String msg;
	private Data data;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public static class Data{
		private AuthenticationBasicInformation authenticationBasicInformation;
		private List<Car> cars;
		public AuthenticationBasicInformation getAuthenticationBasicInformation() {
			return authenticationBasicInformation;
		}
		public void setAuthenticationBasicInformation(
				AuthenticationBasicInformation authenticationBasicInformation) {
			this.authenticationBasicInformation = authenticationBasicInformation;
		}
		public List<Car> getCars() {
			return cars;
		}
		public void setCars(List<Car> cars) {
			this.cars = cars;
		}
		public static class AuthenticationBasicInformation{
			  private String behindTheFrame4Digits;
	          private String identityCard;
	          private String mobilephone;
	          private String myNumberPlate;
	          private String plateType;
	          private String trueName;
			public String getBehindTheFrame4Digits() {
				return behindTheFrame4Digits;
			}
			public void setBehindTheFrame4Digits(String behindTheFrame4Digits) {
				this.behindTheFrame4Digits = behindTheFrame4Digits;
			}
			public String getIdentityCard() {
				return identityCard;
			}
			public void setIdentityCard(String identityCard) {
				this.identityCard = identityCard;
			}
			public String getMobilephone() {
				return mobilephone;
			}
			public void setMobilephone(String mobilephone) {
				this.mobilephone = mobilephone;
			}
			public String getMyNumberPlate() {
				return myNumberPlate;
			}
			public void setMyNumberPlate(String myNumberPlate) {
				this.myNumberPlate = myNumberPlate;
			}
			public String getPlateType() {
				return plateType;
			}
			public void setPlateType(String plateType) {
				this.plateType = plateType;
			}
			public String getTrueName() {
				return trueName;
			}
			public void setTrueName(String trueName) {
				this.trueName = trueName;
			}
		}
		
		public static class Car{
			private String behindTheFrame4Digits;
            private String identityCard;
            private Integer isMySelf;
            private String mobilephone;
            private String myNumberPlate;
            private String name;
            private String plateType;
			public String getBehindTheFrame4Digits() {
				return behindTheFrame4Digits;
			}
			public void setBehindTheFrame4Digits(String behindTheFrame4Digits) {
				this.behindTheFrame4Digits = behindTheFrame4Digits;
			}
			public String getIdentityCard() {
				return identityCard;
			}
			public void setIdentityCard(String identityCard) {
				this.identityCard = identityCard;
			}
			public Integer getIsMySelf() {
				return isMySelf;
			}
			public void setIsMySelf(Integer isMySelf) {
				this.isMySelf = isMySelf;
			}
			public String getMobilephone() {
				return mobilephone;
			}
			public void setMobilephone(String mobilephone) {
				this.mobilephone = mobilephone;
			}
			public String getMyNumberPlate() {
				return myNumberPlate;
			}
			public void setMyNumberPlate(String myNumberPlate) {
				this.myNumberPlate = myNumberPlate;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getPlateType() {
				return plateType;
			}
			public void setPlateType(String plateType) {
				this.plateType = plateType;
			}
		}
	}
}
