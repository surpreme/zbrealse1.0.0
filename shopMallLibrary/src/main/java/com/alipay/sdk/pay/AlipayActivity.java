package com.alipay.sdk.pay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.fragment.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aite.a.APPSingleton;
import com.aite.a.AppManager;
import com.aiteshangcheng.a.R;
import com.aite.a.base.Mark.State;
import com.alipay.sdk.app.PayTask;

public class AlipayActivity extends FragmentActivity {

	// 合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
	public static final String PARTNER = "2088911494702106";
	// 商户收款的支付宝账号
	public static final String SELLER = "caiwu@cnaite.cn";
	// 商户（RSA）私钥(注意一定要转PKCS8格式，否则在Android4.0及以上系统会支付失败)
//	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKiKbb9W9zUVQZFc3JxEa+2USc+3lvRdNRVodUzxVipGvlpyayp34hDL9sRJg9isaH5oRigqUY90+4dHZ/O+Q47/m8ODcUrrRwi1eck4YhftDRXVkbZuXN40TZQQLRNa0AO6rwEuHXqm54dlC8COnesDZvO5uu/Hfs+SwwicjbKFAgMBAAECgYBeY4BJG0xBF1FsNmR6w7rqCBU6VmgdHKIhBipDWBlYfdwXTWMFGrSpkYD2tvE3BH9U49L+pYyd2fcP8QaftBui7owWNUh//CRQSxCOuepI525ceIaHQWZ2MiMafkoWYpf2er0pfGX0fK37+TLT6IuPYFWa60M0TwAb2u/4aeiidQJBANCftJ4URSTdg26Pc0P5i6kJyPcg0TOBGD1N2gmRcS7hKwXam39NzmsDjv4JDDd4Gf2NQvgJ+9/qA+NbFSybwD8CQQDO0IBo9DkD5NdFePzuQaZuN1kDVM8bI3pokbpdZk484SzYxFNQY4qm/m9/ZW76toaCuq2h/UoD8DPq+RUT/pw7AkBajIbbedbM+mwaZ57S6RPqCXo1cQP8A9MegJTbREpI26hNwSVuqrmmDwRYVFr+FF3LhC2F9Odit3J6ksTs+KkHAkEAtXb9NRoVbPU+bhba17OC9PJbiDDCCG342ggEHi/GNmTdlNWuxLqSzjQyYPX/irzdCZgDAj/c0dfRtjsX45NkQQJAatfeHxcdWBb5jP9ApRT5EiHPYaFpuyrKsYNTw7T0dWwFT1ihDA23Gao76yqvvEhIkR0MWEhcgjH2n6uXAsTXvA==";
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALNqaQchugJV9qSPKVPrI/N60myWbhIqZoduc46IQ6snjteDby0nqclyCm4tvz5A5+q+/TjsUvhdVa6unDWiJyfhlQBPOCinRnQoo27Hsu++ufH81BJsKASCGrVT7BzI5c5Qv2efXUv684hCLygc5ZHATQUz9uEFEz+MbztxwYFfAgMBAAECgYEAju8gWzkoSkIX64POJff3vTrFNxMk/73dztlUoGG4nzs1lkY0pSGrJzx+SMaTTFJDMQYVEGtdslYRM8gR1LypOcvfJkhXmEEf/Hsacc52z0hc7g9GK3mkvcRSrrCv9uX+D301FYrQ/QoGEM1l0sXokz9WOxG+0mBmjvBrfFgbLhkCQQDc+/f5yB1XtiFkR++L778RRg40eNlWYGzAYV9W24h/cVxq1SuyRe5rvIxFLOHzM/muBSma4GFD1A8stH1bfbjzAkEAz9g/np5F+o5ebmZtODgm4VFH8uAnhJSoEv80xwiMxAKUtFiNT0PbjnHIvO0XbBI/6uQexY4s4BQPFNnUcUyw5QJAVOJcsI/T5ZtKXFDdjS08gZAdaL57DZjgbU15581QM1QCIe9cZ5BBMxUr9G53JIp0gAnvn1RNSFautYdnF7vFTQJBAJaH7yPJS7N48ymQI2BJQteDT9G2yMg8BjKkBSx1o8W+fMbVL5sN8XEMNa+nI4SQ/xv3FdZM73Fm9blLPpLEUYUCQEwy84ilK0ogsjgH3HBOkKd1qVtCHL1K8geStMWFktK0MHhuZ+Wx6A2awOdKxM+Jqju7rXKG/zMPNUfLVtrSFSA=";
	// 支付宝（RSA）公钥用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
//	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				@SuppressWarnings("unused")
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(AlipayActivity.this, APPSingleton.getContext().getString(R.string.pay_success).toString(), Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(AlipayActivity.this, APPSingleton.getContext().getString(R.string.payment_result_confirm).toString(), Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(AlipayActivity.this, APPSingleton.getContext().getString(R.string.payment_fail).toString(), Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(AlipayActivity.this, APPSingleton.getContext().getString(R.string.check_result).toString() + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	private String pay_sn;
	private String payment_code;
	public static String goods;
	public static String describe;
	public static String price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		AppManager.getInstance().addActivity(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			goods = bundle.getString("goods");
			describe = bundle.getString("describe");
			price = bundle.getString("price");
			pay_sn = bundle.getString("pay_sn");
			payment_code = bundle.getString("payment_code");
			TextView goods_name = (TextView) findViewById(R.id.goods_name);
			TextView goods_describe = (TextView) findViewById(R.id.goods_describe);
			TextView goods_price = (TextView) findViewById(R.id.goods_price);
			TextView goods_pay_sn = (TextView) findViewById(R.id.pay_sn);
			goods_name.setText(goods);
			goods_describe.setText(describe);
			goods_price.setText(String.valueOf(price));
			goods_pay_sn.setText(pay_sn);
		}

	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		// 订单
		String orderInfo = getOrderInfo(goods, describe, String.valueOf(price), pay_sn);
		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(AlipayActivity.this);
				// 调用支付接口，获取支付结果
//				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
//				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(AlipayActivity.this);
				// 调用查询接口，获取查询结果
//				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
//				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price, String pay_sn) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";
		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";
		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo(pay_sn) + "\"";
		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";
		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";
		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";
		payment_code = State.getNotifyUrl(payment_code);
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + payment_code + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";
		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";
		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";
		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";
		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo(String pay_sn) {
		// SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
		// Locale.getDefault());
		// Date date = new Date();
		// String key = format.format(date);
		//
		// Random r = new Random();
		// key = key + r.nextInt();
		// key = key.substring(0, 15);
		return pay_sn;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
