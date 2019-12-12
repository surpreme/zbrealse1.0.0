package com.lzy.basemodule.BaseConstant;


public class AppConstant {
    //ctrl+shfit+u 大写
    //ctrl+alt+m 实现方法
    //@SerializedName(value = "name", alternate = {"Name", "studentName", "fullName"})中有两个属性,value="默认key",alternate="别名key1,别名key1..."，别名可以有多个，多个别名用“,”分隔,便降低了数据别名不统一造成的解析异常
    public static final String REGTYPE = "Ismobilereg";
    public static final String CLIENT = "android";
    public static String KEY = null;
    public static String USERNAME = null;
    public static String FRIEND_VALID = null;
    public static String MEMBER_ID = null;
    public static String ICON_URL = null;
    public static String PHONENUMBER = null;

    private static final String BASEURL = "http://zhongbyi.aitecc.com/mobile/";
    //  获取所有地区数据
    public static final String AREACHIOCEHELPDOCTORNEEDURL = BASEURL + "index.php?act=index&op=getAllAreaList";
    //注册验证码
    public static final String POSTNUMBERCODE = BASEURL + "index.php?act=login&op=send_modify_mobile";
    //找回密码验证码
    public static final String FINDKEYCODE = BASEURL + "index.php?act=login&op=send_findpass_mobile";
    //找回密码验证码-验证
    public static final String SUREFINDKEYCODE = BASEURL + "index.php?act=login&op=check_verification_code";
    //找回密码-提交
    public static final String ALLSUREFINDKEYCODE = BASEURL + "index.php?act=login&op=setting_password";

    //注册第一步提交
    public static final String NEWUSERFIRSTURL = BASEURL + "index.php?act=login&op=register";
    //注册第2步提交
    public static final String TWONEWUSERFIRSTURL = BASEURL + "index.php?act=login&op=register2";
    //3
    public static final String THREENEWUSERFIRSTURL = BASEURL + "index.php?act=login&op=register3";


    //登录
    public static final String LOGINURL = BASEURL + "index.php?act=login&op=index";
    //
    //获取支付方式 注：如果使用钱包支付 则单独调用对应钱包支付提交接口
    public static final String PAYAWYGETINFORMATIONURL = BASEURL + "index.php?act=member_payment&op=APP_getPaymentList";
    //app首页
    public static final String MAINUIDATAURL = BASEURL + "index.php?act=index&op=APPindex";
    //添加地址 -获取地址列表信息
    public static final String LISTINFORMATIONADDRESSDATAURL = BASEURL + "index.php?act=member_address&op=address_list";
    //添加地址 -地址详细信息
    public static final String INFORMATIONADDRESSPERSONDATAURL = BASEURL + "index.php?act=member_address&op=address_info";
    //添加地址-提交
    public static final String ADDADDRESSDATAURL = BASEURL + "index.php?act=member_address&op=address_add";
    //健康档案-得到信息
    public static final String GETHEALTHINFORMATIONDATAURL = BASEURL + "index.php?act=health_record&op=detail";
    //健康档案-子页面提交
    public static final String CHDRENADDHEALTHINFORMATIONDATAURL = BASEURL + "index.php?act=health_record&op=health_add";
    //健康档案-疾病史/医疗笔记/过敏反应/药物使用列表信息
    public static final String LISTMSGHEALTHINFORMATIONDATAURL = BASEURL + "index.php?act=health_record&op=health_list";
    // index.php?act=member_timebank&op=my_qrcode

    //健康档案-主页面提交
    public static final String MIANADDHEALTHINFORMATIONDATAURL = BASEURL + "index.php?act=health_record&op=info_add";
    //助餐首页
    public static final String HELPEATUIURL = BASEURL + "index.php?act=goods_meal&op=index";
    //早、午餐——商品列表信息
    public static final String MORNINGNOONTHINGLISTURL = BASEURL + "index.php?act=goods_meal&op=meal_goods_list";
    //菜品选择页面信息
    public static final String CHOOCELISTURL = BASEURL + "index.php?act=goods_meal&op=dishes_goods";
    //菜品选择页面信息2
    public static final String CHOOCESENCONDLISTURL = BASEURL + "index.php?act=goods_meal&op=dishes_goods_list";
    //早、午餐——预约订餐 第一步 设置信息
    public static final String REMEMBERFOODINFORMATIONURL = BASEURL + "index.php?act=goods_meal&op=meal_buy1";
    //早、午餐——预约订餐 第二步 产生订单
    public static final String SUREREMEMBERFOODINFORMATIONURL = BASEURL + "index.php?act=goods_meal&op=meal_buy2";
    //
    //订单详情接口 index.php?act=member_vr_order&op=order_detail
    public static final String ALLBOOKINFORMATIONURL = BASEURL + "index.php?act=member_vr_order&op=order_detail";
    //
    //助餐服务——早、午餐订单列表
    public static final String MINEALLBOOKURL = BASEURL + "index.php?act=member_vr_order&op=meal_order_list";
    //时间银行 获取服务列表页筛选条件及广告信息接口
    public static final String EALSETIMEBANKSERVICEMAINUIURL = BASEURL + "index.php?act=timebank&op=list_search";
    //时间银行 服务列表页信息接口
    public static final String TIMEBANKSERVICEMAINUIURL = BASEURL + "index.php?act=timebank&op=list";
    //时间银行 服务申请接口
    public static final String POSTTIMEBANKSERVICEURL = BASEURL + "index.php?act=timebank&op=apply";
    //时间银行获取服务分类
    public static final String GETBANKSERVICETYPEURL = BASEURL + "index.php?act=timebank&op=class";
    // index.php?act=timebank&op=detail
    //时间银行 服务详情页接口
    public static final String BOOKINFORMATIONTIMEBANKURL = BASEURL + "index.php?act=timebank&op=detail";
    //时间银行 服务接单
    public static final String STARTHELPTIMEBANKURL = BASEURL + "index.php?act=timebank&op=order";
    //
    //获取积分银行页信息
    public static final String NUMBERBANKINFORMATIONURL = BASEURL + "index.php?act=member_timebank_point&op=index";
    //获取积分银行  获取会员积分记录
    public static final String AGOGETNUMBERBANKINFORMATIONURL = BASEURL + "index.php?act=member_timebank_point&op=points_log_list";
    //获取积分礼品
    public static final String NUMBERSHOPLISTINFORMATIONURL = BASEURL + "index.php?act=member_timebank_point&op=points_goods_list";
    //获取积分规则页信息 直接返回H5内容
    public static final String RULESNUMBERINFORMATIONURL = BASEURL + "index.php?act=member_timebank_point&op=rule";
    //
    // index.php?act=timebank&op=order
    // index.php?act=timebank&op=detail
    //喘息服务相关接口 获取服务列表页筛选条件及广告信息接口
    public static final String EALSETIMEAIRSERVICEMAINUIURL = BASEURL + "index.php?act=timebank&op=list_search";
    //喘息服务相关接口 服务申请接口
    public static final String POSTAIRBANKSERVICEURL = BASEURL + "index.php?act=respite&op=apply";
    //喘息服务相关接口 服务列表页信息接口
    public static final String AIRLISTRECYURL = BASEURL + "index.php?act=respite&op=list";

    //喘息服务相关接口 获取服务分类数据接口
    public static final String TYCHOICEAIRINFORMATIONURL = BASEURL + "index.php?act=respite&op=class";
    //喘息服务相关接口 服务详情页接口
    public static final String INFORMATIONAIRINSERVICEURL = BASEURL + "index.php?act=respite&op=detail";
    //喘息服务相关接口 服务接单
    public static final String STARTAIRINSERVICEURL = BASEURL + "index.php?act=respite&op=order";
    //核销码接口 时间银行-喘息 助医
    public static final String SUREBOOKSERVICETIMEBANKURL = BASEURL + "index.php?act=member_timebank&op=verify";
    //购物车 添加
    public static final String ADDSHOPCARDURL = BASEURL + "index.php?act=member_cart&op=app_cart_add";

    //购物车 列表
    public static final String SHOPCARDLISTURL = BASEURL + "index.php?act=member_cart&op=cart_list";

    //删除购物车 删除多条购物车时用 ‘|’ 号隔开
    public static final String DELETESHOPCARDLISTURL = BASEURL + "index.php?act=member_cart&op=cart_del";
    //购物车  加减数量
    public static final String ADDLESSSHOPCARDLISTURL = BASEURL + "index.php?act=member_cart&op=cart_edit_quantity";
    //助医  申请服务
    public static final String POSTHELPDOCTORNEEDURL = BASEURL + "index.php?act=doctor_help&op=apply";
    //助医  服务列表 服务列表页信息接口
    public static final String LISTHELPDOCTORNEEDURL = BASEURL + "index.php?act=doctor_help&op=list";

    //助医  获取服务列表页筛选条件及广告信息接口 index.php?act=doctor_help&op=list_search
    public static final String LELSEHELPDOCTORNEEDURL = BASEURL + "index.php?act=doctor_help&op=list_search";
    //助医  服务详情页接口
    public static final String INFORMATIONHELPDOCTORNEEDURL = BASEURL + "index.php?act=doctor_help&op=detail";

    //助医  服务接单
    public static final String STARTHELPDOCTORNEEDURL = BASEURL + "index.php?act=doctor_help&op=order";
    //助医 申请服务  获取分类
    public static final String TYPECHIOCEHELPDOCTORNEEDURL = BASEURL + "index.php?act=doctor_help&op=class";
    //上传个人基本信息
    public static final String POSTPEPPLEINFORMATIONURL = BASEURL + "index.php?act=member_index&op=APP_editInfo";
    //得到个人基本信息
    public static final String GETPEPPLEINFORMATIONURL = BASEURL + "index.php?act=member_index&op=getInfo";
    //
    //我的紧急联系人 - 列表接口
    public static final String LISTSOSPEPPLEINFORMATIONURL = BASEURL + "index.php?act=member_contact&op=my_list";
    //我的关联账号 - 列表接口
    public static final String LISTPEPPLEINFORMATIONURL = BASEURL + "index.php?act=member_associate&op=my_list";

    //我的关联账号-获取关系信息
    public static final String GETFIMILYBINDINGUSERURL = BASEURL + "index.php?act=member_associate&op=relation";
    //我的关联账号-保存接口
    public static final String SAVEBINDINGUSERURL = BASEURL + "index.php?act=member_associate&op=my_save";
    // index.php?act=member_contact&op=my_save
    //紧急联系人-保存接口
    public static final String SAVESOSUSERURL = BASEURL + "index.php?act=member_contact&op=my_save";
    // index.php?act=member_nursing_store&op=Untied
    //养老院 - 点击解绑接口
    public static final String UNBINDHELPELDERHOUSEURL = BASEURL + "index.php?act=member_nursing_store&op=Untied";
    //养老院 - 点击绑定接口
    public static final String BINDHELPELDERHOUSEURL = BASEURL + "index.php?act=member_nursing_store&op=Binding";
    //养老院 - 列表接口
    public static final String HELPELDERHOUSEURL = BASEURL + "index.php?act=member_nursing_store&op=list";
    /**
     * 发布
     */
    //时间银行 我发布的服务——列表信息
    public static final String POSTTIMEBANKMINETOGETHERLISTURL = BASEURL + "index.php?act=member_timebank&op=my_list";
    //时间银行 我发布的服务——服务详情
    public static final String POSTTIMEBANKINFORMATIONMINETOGETHERURL = BASEURL + "index.php?act=member_timebank&op=my_detail";
    //喘息服务 我发布的服务——列表信息
    public static final String POSTAIRSERVICEMINETOGETHERLISTURL = BASEURL + "index.php?act=member_respite&op=my_list";
    //喘息服务 我发布的服务——服务详情
    public static final String POSTAIRSERVICEINFORMATIONMINETOGETHERURL = BASEURL + "index.php?act=member_respite&op=my_detail";
    //助医服务 我发布的服务——列表信息
    public static final String POSTHELPDOCTORMINETOGETHERLISTURL = BASEURL + "index.php?act=member_doctor_help&op=my_list";
    //助医服务 我发布的服务——服务详情
    public static final String POSTHELPDOCTORINFORMATIONMINETOGETHERURL = BASEURL + "index.php?act=member_doctor_help&op=my_detail";
    /**
     *
     */
    /**
     * 参与
     */
    //时间银行 我参与的服务——列表信息
    public static final String TIMEBANKMINETOGETHERLISTURL = BASEURL + "index.php?act=member_timebank&op=order_list";
    //时间银行 我参与的服务——服务详情
    public static final String TIMEBANKINFORMATIONMINETOGETHERURL = BASEURL + "index.php?act=member_timebank&op=order_detail";
    //喘息服务 我参与的服务——列表信息
    public static final String AIRSERVICEMINETOGETHERLISTURL = BASEURL + "index.php?act=member_respite&op=order_list";
    //喘息服务 我参与的服务——服务详情
    public static final String AIRSERVICEINFORMATIONMINETOGETHERURL = BASEURL + "index.php?act=member_respite&op=order_detail";
    //助医服务 我参与的服务——列表信息
    public static final String HELPDOCTORMINETOGETHERLISTURL = BASEURL + "index.php?act=member_doctor_help&op=order_list";
    //助医服务 我参与的服务——服务详情
    public static final String HELPDOCTORINFORMATIONMINETOGETHERURL = BASEURL + "index.php?act=member_doctor_help&op=order_detail";
    /**
     *
     */
    // index.php?act=member_respite&op=order_list
    //APP残疾之家商品列表筛选条件信息
    public static final String CHOICEMSGLESSBODYLISTURL = BASEURL + "index.php?act=goods&op=dhome_list_search";
    //APP残疾之家商品列表信息
    public static final String ALLLESSBODYLISTURL = BASEURL + "index.php?act=goods&op=dhome_goods_list";
    //APP残疾之家 APP残疾之家商品详情
    public static final String INFORMATIONALLLESSBODYLISTURL = BASEURL + "index.php?act=goods&op=dhome_goods_detail";
    //APP残疾之家 APP残疾之家商品收藏
    public static final String COLLECTLLESSBODYLISTURL = BASEURL + "index.php?act=goods&op=favoritesgoods";
    //APP残疾之家——订单列表
    public static String BOOKINFORMATIONLISTMINELISTURL = BASEURL + "index.php?act=member_vr_order&op=dhome_order_list";
    //APP残疾之家—— 虚拟商品购买第二步，设置接收手机号页面
    public static String BUYSECONDINFORMATIONLISTMINELISTURL = BASEURL + "index.php?act=member_vr_buy&op=buy_step2";
    //APP残疾之家—— 虚拟订单第三步，产生订单
    public static String OVERBUYINFORMATIONLISTMINELISTURL = BASEURL + "index.php?act=member_vr_buy&op=buy_step3";
    //  index.php?act=member_timebank&op=order_start

    //我参与的服务-服务开始接口
    public static String STARTSERVICEPOSTIMNGURL = BASEURL + "index.php?act=member_timebank&op=order_start";
    //我参与的服务-服务结束接口
    public static String ENDSERVICEPOSTIMNGURL = BASEURL + "index.php?act=member_timebank&op=order_end";

    public static String AITEURL = "https://aitecc.com/mobile/";
    //圈子
    public static String NEWSURL = AITEURL + "index.php?act=sns";

    // * 底部推荐新闻

    public static String RECOMMENDED_NEWS = AITEURL + "index.php?act=cms&op=article_index_commend";
    /**
     * * 顶部推荐新闻
     */
    public static String TOP_NEWS = AITEURL + "index.php?act=cms&op=article_index_top";
    /**
     * 新闻详情
     */
    public static String NEWS_INFO = AITEURL
            + "index.php?act=cms&op=article_show";

    // * 我的社区 发布列表页信息
    public static String MINECOLLECTPOSTLIST = AITEURL + "index.php?act=member_circle&op=Release_list";
    // index.php?act=member_circle&op=Release_list


    /**
     * 手表服务器
     */
//    http://watchapi.topqizhi.com/solgoWatch/deviceGetServerIp.do
    public static String DEVICEURL = "https://watchapi.topqizhi.com/solgoWatch/";

    public static String STARTDEVICEURL = DEVICEURL + "deviceGet0ServerIp.do";
    //deviceParamInit.do  http://watchapi.topqizhi.com/solgoWatch/deviceParamInit.do
    public static String GETDEVICEINFORMATIONURL = DEVICEURL + "deviceParamInit.do";
    //心率数据查询
    public static String GETDEVICEHEADINFORMATIONURL = DEVICEURL + "HEART_QUERY";

}
