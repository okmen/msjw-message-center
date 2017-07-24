package cn.message.utils.wechat;

public class CardUtil {
	//gettoken url
	//正式 http://szjj.u-road.com/api/wechat/asdasdasdas.html?key=qxwsed@!s1334
	
	//测试  http://testjava.chudaokeji.com/wechat/asdasdasdas.html?key=qxwsed@!s1334
	static String token = "rkmlJ3HBjlBpTYZdhxijEdlQY9SCWyO0y7vypjF7G3J-KjP-IXTanEelvmtfRYtE6KmbHkgHSdGaeQtZQB7S0ETutbzgalxb4zjiBHE8sLsHAEfAAAOHL";
	public static String getCardList(){
		String url = "https://api.weixin.qq.com/card/batchget?access_token="+token;
		String data = "{"+
					"\"offset\": 0,"+
					"\"count\": 50,"+
					"\"status_list\": [\"CARD_STATUS_VERIFY_OK\", \"CARD_STATUS_DISPATCH\"]"+
				"}";
		return HttpRequest.sendPost(url, data);
	}
	
	public static String getCardInfo(String cardId){
		String url = "https://api.weixin.qq.com/card/get?access_token="+token;
		String data = "{"+
					"\"card_id\":\""+cardId+"\""+
				"}";
		return HttpRequest.sendPost(url, data);
	}
	
	public static String createCard(){
		String url = "https://api.weixin.qq.com/card/create?access_token="+token;
		String data = CardModel.init();
		String json = HttpRequest.sendPost(url, data);
		return json;
	}
	
	
	public static String updateCard(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String data = CardModelUpdate.init();
		String json = HttpRequest.sendPost(url, data);
		return json;
	}
	public static void main(String[] args) {
		/*String cardList = getCardList();
		Map<String, Object> map = GsonUtil.fromJson(cardList, Map.class);
		List<String> list = (List<String>)map.get("card_id_list");
		for (String string : list) {
			String cardInfo = getCardInfo(string);
			System.out.println(cardInfo);
		}*/
   
		/*String result = createCard();*/
		
		String result = updateCard();
		System.out.println("result:"+result);
	}
}
