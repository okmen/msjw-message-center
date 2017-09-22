package cn.message.utils.wechat;

import cn.message.utils.wechat.CardGeneral.GeneralCard.BaseInfo;
import cn.message.utils.wechat.CardGeneral.GeneralCard.CustomCell;
import cn.sdk.util.GsonBuilderUtil;


public class CardGeneral {
		private String card_id;
		private GeneralCard general_card;
		public String getCard_id() {
			return card_id;
		}
		public void setCard_id(String card_id) {
			this.card_id = card_id;
		}
		public GeneralCard getGeneral_card() {
			return general_card;
		}
		public void setGeneral_card(GeneralCard general_card) {
			this.general_card = general_card;
		}
		static class GeneralCard{
			private String background_pic_url;
			private BaseInfo base_info;
			private Boolean supply_bonus;
			private Boolean supply_balance;
			private String prerogative;
			private Boolean auto_activate;
			private String activate_url;
			private CustomCell custom_cell1;
			private CustomCell custom_cell2;

			public String getBackground_pic_url() {
				return background_pic_url;
			}

			public void setBackground_pic_url(String background_pic_url) {
				this.background_pic_url = background_pic_url;
			}

			public BaseInfo getBase_info() {
				return base_info;
			}

			public void setBase_info(BaseInfo base_info) {
				this.base_info = base_info;
			}

			public Boolean getSupply_bonus() {
				return supply_bonus;
			}

			public void setSupply_bonus(Boolean supply_bonus) {
				this.supply_bonus = supply_bonus;
			}

			public Boolean getSupply_balance() {
				return supply_balance;
			}

			public void setSupply_balance(Boolean supply_balance) {
				this.supply_balance = supply_balance;
			}

			public String getPrerogative() {
				return prerogative;
			}

			public void setPrerogative(String prerogative) {
				this.prerogative = prerogative;
			}

			public Boolean getAuto_activate() {
				return auto_activate;
			}

			public void setAuto_activate(Boolean auto_activate) {
				this.auto_activate = auto_activate;
			}

			public String getActivate_url() {
				return activate_url;
			}

			public void setActivate_url(String activate_url) {
				this.activate_url = activate_url;
			}

			public CustomCell getCustom_cell1() {
				return custom_cell1;
			}

			public void setCustom_cell1(CustomCell custom_cell1) {
				this.custom_cell1 = custom_cell1;
			}

			public CustomCell getCustom_cell2() {
				return custom_cell2;
			}

			public void setCustom_cell2(CustomCell custom_cell2) {
				this.custom_cell2 = custom_cell2;
			}
			public GeneralCard(String background_pic_url, BaseInfo base_info,
					Boolean supply_bonus, Boolean supply_balance,
					String prerogative, Boolean auto_activate,
					String activate_url, CustomCell custom_cell1,
					CustomCell custom_cell2) {
				super();
				this.background_pic_url = background_pic_url;
				this.base_info = base_info;
				this.supply_bonus = supply_bonus;
				this.supply_balance = supply_balance;
				this.prerogative = prerogative;
				this.auto_activate = auto_activate;
				this.activate_url = activate_url;
				this.custom_cell1 = custom_cell1;
				this.custom_cell2 = custom_cell2;
			}

			public GeneralCard(){}
			static class BaseInfo{
				private String logo_url;
				private String brand_name;
				private String code_type;
				private String title;
				private String color;
				private String notice;
				private String service_phone;
				private String description;
				private DateInfo date_info;
				private Sku sku;
				private Integer get_limit;
				private Boolean can_give_friend;
				private String center_title;
				private String center_url;
				private Boolean can_share;
				private Boolean use_custom_code;
				private String promotion_url_name;
				private String promotion_url;
				private String promotion_url_sub_title;
				private Boolean need_push_on_view;
				
				public String getLogo_url() {
					return logo_url;
				}

				public void setLogo_url(String logo_url) {
					this.logo_url = logo_url;
				}

				public String getBrand_name() {
					return brand_name;
				}

				public void setBrand_name(String brand_name) {
					this.brand_name = brand_name;
				}

				public String getCode_type() {
					return code_type;
				}

				public void setCode_type(String code_type) {
					this.code_type = code_type;
				}

				public String getTitle() {
					return title;
				}

				public void setTitle(String title) {
					this.title = title;
				}

				public String getColor() {
					return color;
				}

				public void setColor(String color) {
					this.color = color;
				}

				public String getNotice() {
					return notice;
				}

				public void setNotice(String notice) {
					this.notice = notice;
				}

				public String getService_phone() {
					return service_phone;
				}

				public void setService_phone(String service_phone) {
					this.service_phone = service_phone;
				}

				public String getDescription() {
					return description;
				}

				public void setDescription(String description) {
					this.description = description;
				}

				public DateInfo getDate_info() {
					return date_info;
				}

				public void setDate_info(DateInfo date_info) {
					this.date_info = date_info;
				}

				public Sku getSku() {
					return sku;
				}

				public void setSku(Sku sku) {
					this.sku = sku;
				}

				public Integer getGet_limit() {
					return get_limit;
				}

				public void setGet_limit(Integer get_limit) {
					this.get_limit = get_limit;
				}

				public Boolean getCan_give_friend() {
					return can_give_friend;
				}

				public void setCan_give_friend(Boolean can_give_friend) {
					this.can_give_friend = can_give_friend;
				}

				public String getCenter_title() {
					return center_title;
				}

				public void setCenter_title(String center_title) {
					this.center_title = center_title;
				}

				public String getCenter_url() {
					return center_url;
				}

				public void setCenter_url(String center_url) {
					this.center_url = center_url;
				}

				public Boolean getCan_share() {
					return can_share;
				}

				public void setCan_share(Boolean can_share) {
					this.can_share = can_share;
				}

				public Boolean getUse_custom_code() {
					return use_custom_code;
				}

				public void setUse_custom_code(Boolean use_custom_code) {
					this.use_custom_code = use_custom_code;
				}

				public String getPromotion_url_name() {
					return promotion_url_name;
				}

				public void setPromotion_url_name(String promotion_url_name) {
					this.promotion_url_name = promotion_url_name;
				}

				public String getPromotion_url() {
					return promotion_url;
				}

				public void setPromotion_url(String promotion_url) {
					this.promotion_url = promotion_url;
				}

				public String getPromotion_url_sub_title() {
					return promotion_url_sub_title;
				}

				public void setPromotion_url_sub_title(String promotion_url_sub_title) {
					this.promotion_url_sub_title = promotion_url_sub_title;
				}

				public Boolean getNeed_push_on_view() {
					return need_push_on_view;
				}

				public void setNeed_push_on_view(Boolean need_push_on_view) {
					this.need_push_on_view = need_push_on_view;
				}
				public BaseInfo(String logo_url, String brand_name,
						String code_type, String title, String color,
						String notice, String service_phone,
						String description, DateInfo date_info, Sku sku,
						Integer get_limit, Boolean can_give_friend,
						String center_title, String center_url,
						Boolean can_share, Boolean use_custom_code,
						String promotion_url_name, String promotion_url,
						String promotion_url_sub_title,
						Boolean need_push_on_view) {
					super();
					this.logo_url = logo_url;
					this.brand_name = brand_name;
					this.code_type = code_type;
					this.title = title;
					this.color = color;
					this.notice = notice;
					this.service_phone = service_phone;
					this.description = description;
					this.date_info = date_info;
					this.sku = sku;
					this.get_limit = get_limit;
					this.can_give_friend = can_give_friend;
					this.center_title = center_title;
					this.center_url = center_url;
					this.can_share = can_share;
					this.use_custom_code = use_custom_code;
					this.promotion_url_name = promotion_url_name;
					this.promotion_url = promotion_url;
					this.promotion_url_sub_title = promotion_url_sub_title;
					this.need_push_on_view = need_push_on_view;
				}
				public BaseInfo(){}
				static class DateInfo{
					private String type;
					public String getType() {
						return type;
					}
					public void setType(String type) {
						this.type = type;
					}
					public DateInfo(String type) {
						super();
						this.type = type;
					}
					public DateInfo(){}
				}
				
				static class Sku{
					private Integer quantity;
					public Integer getQuantity() {
						return quantity;
					}
					public void setQuantity(Integer quantity) {
						this.quantity = quantity;
					}
					public Sku(Integer quantity) {
						super();
						this.quantity = quantity;
					}
					
					public Sku(){}
				}
			}
			
			static class CustomCell{
				private String name;
				private String tips;
				private String url;
				public String getName() {
					return name;
				}
				public void setName(String name) {
					this.name = name;
				}
				public String getTips() {
					return tips;
				}
				public void setTips(String tips) {
					this.tips = tips;
				}
				public String getUrl() {
					return url;
				}
				public void setUrl(String url) {
					this.url = url;
				}
				public CustomCell(String name, String tips, String url) {
					super();
					this.name = name;
					this.tips = tips;
					this.url = url;
				}
				public CustomCell(){}
			}
		}
	
	
	public static String init() {
		CardGeneral cardModel = new CardGeneral();
		
		cardModel.setCard_id("pILMDwI0YyTv1ikElE4IOHDxU00I");
		GeneralCard member_card = new GeneralCard();
		member_card.setActivate_url("http://testjava.chudaokeji.com/h5/activeXsCard.html");
		
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setPromotion_url("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/infoquery/policeinteraction/failureReport&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		member_card.setBase_info(baseInfo);
		
		cardModel.setGeneral_card(member_card);
		
		String json = GsonBuilderUtil.toJson(cardModel);
		System.out.println(json);
		return json;
	}
	
	
	/**
	 * 
	 * 驾驶证卡
	 * @param cardId
	 * @param promotion_url
	 * @param center_url
	 * @param activate_url
	 * @param custom_cell1_url
	 * @param custom_cell2_url
	 * @return
	 */
	public static String update(String cardId,String promotion_url,String center_url,String activate_url,String custom_cell1_url){
		CardGeneral cardModel = new CardGeneral();
		cardModel.setCard_id(cardId);
		GeneralCard member_card = new GeneralCard();
		
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setPromotion_url(promotion_url);
		baseInfo.setCenter_url(center_url);
		
		CustomCell custom_cell1 = new CustomCell();
		custom_cell1.setUrl(custom_cell1_url);

		member_card.setCustom_cell1(custom_cell1);
		
		member_card.setActivate_url(activate_url);
		member_card.setBase_info(baseInfo);
		
		cardModel.setGeneral_card(member_card);
		String json = GsonBuilderUtil.toJson(cardModel);
		return json;
	}
	
	/**
	 * 行驶证卡 
	 * @param cardId
	 * @param promotion_url
	 * @param center_url
	 * @param activate_url
	 * @param custom_cell1_url
	 * @param custom_cell2_url
	 * @return
	 */
	public static String update(String cardId,String promotion_url,String center_url,String activate_url,String custom_cell1_url,String custom_cell2_url){
		CardGeneral cardModel = new CardGeneral();
		cardModel.setCard_id(cardId);
		GeneralCard member_card = new GeneralCard();
		
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setPromotion_url(promotion_url);
		baseInfo.setCenter_url(center_url);
		
		CustomCell custom_cell1 = new CustomCell();
		custom_cell1.setUrl(custom_cell1_url);

		CustomCell custom_cell2 = new CustomCell();
		custom_cell2.setUrl(custom_cell2_url);
		
		member_card.setCustom_cell1(custom_cell1);
		member_card.setCustom_cell2(custom_cell2);
		
		member_card.setActivate_url(activate_url);
		member_card.setBase_info(baseInfo);
		
		cardModel.setGeneral_card(member_card);
		String json = GsonBuilderUtil.toJson(cardModel);
		return json;
	}
}