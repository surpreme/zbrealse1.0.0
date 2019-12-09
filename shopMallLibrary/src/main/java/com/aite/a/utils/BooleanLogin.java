package com.aite.a.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.aiteshangcheng.a.R;
import com.aite.a.activity.EdiAddressActivity;
import com.aite.a.activity.LoginActivity;
import com.aite.a.base.Mark;
import com.aite.a.model.BuySteOneInfo;
import com.aite.a.view.Dialog;
import com.aite.a.view.Dialog.DialogClickListener;

public class BooleanLogin {
	public static BooleanLogin login;

	public static BooleanLogin getInstance() {
		if (login == null) {
			login = new BooleanLogin();
		}
		return login;
	}

	public boolean hasLogin(final Context _context) {
		if (Mark.State.UserKey == null) {
			Dialog.showSelectDialog(_context, _context.getString(R.string.not_login).toString(), new DialogClickListener() {

				@Override
				public void confirm() {
					Intent intent = new Intent(_context, LoginActivity.class);
					_context.startActivity(intent);
					((Activity) _context).overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				}

				@Override
				public void cancel() {
				}
			});
			return false;
		} else {
			return true;
		}
	}

	public boolean hasAddress(final Context _context, BuySteOneInfo buySteOneInfo) {
		if (buySteOneInfo.addressInfo.address_id == null) {
			Dialog.showSelectDialog(_context, _context.getString(R.string.not_set_address).toString(), new DialogClickListener() {

				@Override
				public void confirm() {
					Intent intent = new Intent(_context, EdiAddressActivity.class);
					_context.startActivity(intent);
					((Activity) _context).finish();
					((Activity) _context).overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
				}

				@Override
				public void cancel() {
				}
			});
			return false;
		} else {
			return true;
		}
	}
}
