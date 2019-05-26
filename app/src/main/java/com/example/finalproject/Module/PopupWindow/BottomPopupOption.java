package com.example.finalproject.Module.PopupWindow;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.finalproject.Fragment.FoodListFrag.Food;
import com.example.finalproject.R;
import com.example.finalproject.Data.User;
import com.example.finalproject.Data.myStatic;
import com.zkk.view.rulerview.RulerView;

public class BottomPopupOption extends Activity {
    //上下文对象
    private Context mContext;
    //Title文字
    private String mTitle;
    //PopupWindow对象
    private PopupWindow mPopupWindow;
    //选项的文字
    private String[] options;
    //选项的颜色
    private int[] Colors;
    //点击事件
    private onPopupWindowItemClickListener itemClickListener;
    //全局变量引用
    User tester = myStatic.getInstance().getTester();
    SQLiteDatabase db = myStatic.getInstance().getDb();
    ContentValues values;
    Cursor cursor;
    //食物选择的rulerview
    private RulerView ruler_food;
    private TextView foodAmt;
    private RulerView ruler_weight;
    //体重选择的rulerview
    private TextView weight;
    private float ruler_food_value;
    private float ruler_weight_value_cur;
    private float ruler_weight_value_target;


    public void setTester(User tester) {
        this.tester = tester;
    }

    public User getTester() {
        return tester;
    }

    /**
     * 一个参数的构造方法，用于弹出无标题的PopupWindow
     *
     * @param context
     */
    public BottomPopupOption(Context context) {
        this.mContext = context;
    }

    /**
     * 2个参数的构造方法，用于弹出有标题的PopupWindow
     *
     * @param context
     * @param title   标题
     */
    public BottomPopupOption(Context context, String title, User tester) {
        this.mContext = context;
        this.mTitle = title;
        this.tester = tester;
    }

    /**
     * 设置选项的点击事件
     *
     * @param itemClickListener
     */
    public void setItemClickListener(onPopupWindowItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 设置选项文字
     */
    public void setItemText(String... items) {
        options = items;
    }

    /**
     * 设置选项文字颜色，必须要和选项的文字对应
     */
    public void setColors(int... color) {
        Colors = color;
    }

    /**
     * 弹出Popupwindow
     */
//食物选择的popupwindow
    public void showPopupWindow(final Food food, int pat) {
        View popupWindow_view = LayoutInflater.from(mContext).inflate(R.layout.popup_window, null);
        addView(popupWindow_view, food, pat);
        //添加子Vi;
        mPopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, 1000);
        mPopupWindow.setAnimationStyle(R.anim.enter_from_bottom);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(popupWindow_view);
    }

    public void addView(View view, final Food food, final int pat) {
        ruler_food = (RulerView) view.findViewById(R.id.ruler_height);
        foodAmt = (TextView) view.findViewById(R.id.tv_register_info_height_value);
        ruler_food_value = 100;
        ruler_food.setValue(100, 0, 500, 1);
        foodAmt.setText(100 + "g");
        ruler_food.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                foodAmt.setText((int) value + "g");
                ruler_food_value = value;
            }
        });
        final Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                food.setAmt((int) ruler_food_value);
                switch (pat) {
                    case 1:
                        tester.addBreakfast(food, food.getAmt());
                        values = new ContentValues();
                        values.put("email",tester.getName());
                        values.put("amt", food.getAmt());
                        values.put("name", food.getName());
                        values.put("picId", food.getPicId());
                        values.put("cal", food.getCalorie());
                        values.put("year", myStatic.getInstance().getYear());
                        values.put("month", myStatic.getInstance().getMonth());
                        values.put("date", myStatic.getInstance().getDate());
                        db.insert("Breakfast", null, values);
                        values.clear();
                        break;
                    case 2:
                        tester.addLunch(food, food.getAmt());
                         values = new ContentValues();
                        values.put("email",tester.getName());
                        values.put("amt", food.getAmt());
                        values.put("name", food.getName());
                        values.put("picId", food.getPicId());
                        values.put("cal", food.getCalorie());
                        values.put("year", myStatic.getInstance().getYear());
                        values.put("month", myStatic.getInstance().getMonth());
                        values.put("date", myStatic.getInstance().getDate());
                        db.insert("Lunch", null, values);
                        values.clear();
                        break;
                    case 3:
                        tester.addDinner(food, food.getAmt());
                        values = new ContentValues();
                        values.put("email",tester.getName());
                        values.put("amt", food.getAmt());
                        values.put("name", food.getName());
                        values.put("picId", food.getPicId());
                        values.put("cal", food.getCalorie());
                        values.put("year", myStatic.getInstance().getYear());
                        values.put("month", myStatic.getInstance().getMonth());
                        values.put("date", myStatic.getInstance().getDate());
                        db.insert("Dinner", null, values);
                        values.clear();
                        break;
                }
                dismiss();
            }
        });


    }

    //体重的popupiwndow
    public void showPopupWindow(int pat) {
        View popupWindow_view = LayoutInflater.from(mContext).inflate(R.layout.popup_window, null);
        //添加子View
        addView(popupWindow_view, pat);
        mPopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, 1000);
        mPopupWindow.setAnimationStyle(R.anim.enter_from_bottom);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
        show(popupWindow_view);
    }

    public void addView(View view, final int pat) {
        ruler_weight = (RulerView) view.findViewById(R.id.ruler_height);
        weight = (TextView) view.findViewById(R.id.tv_register_info_height_value);
        cursor=db.query("UserInfo",null,"email=?",new String[]{tester.getName()},null,null,null);
     if (pat == 1) {
            weight.setText(tester.getWeight() + "kg");
            ruler_weight.setValue(tester.getWeight(), 30, 200, 0.1f);
            ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
                @Override
                public void onValueChange(float value) {
                    weight.setText(value + "kg");
                    ruler_weight_value_cur= value;
                }
            });

            final Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tester.setWeight(ruler_weight_value_cur);
                    if (cursor.moveToFirst()) {
                        do {
// 遍历Cursor对象，取出数据并打印
                            ContentValues values = new ContentValues();
                            values.put("weight", ruler_weight_value_cur);
                            db.update("UserInfo", values, "email = ?", new String[] {tester.getName()});
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    func();
                    dismiss();
                }
            });
        } else {
            weight.setText(tester.getWeightTarget() + "kg");
            ruler_weight.setValue(tester.getWeightTarget(), 30, 200, 0.1f);
            ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
                @Override
                public void onValueChange(float value) {
                    weight.setText(value + "kg");
                    ruler_weight_value_target = value;
                }
            });

            final Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tester.setWeightTarget(ruler_weight_value_target);
                    tester.setWeight_record(tester.getWeight());
                    if (cursor.moveToFirst()) {
                        do {
// 遍历Cursor对象，取出数据并打印
                            ContentValues values = new ContentValues();
                            values.put("weightTarget", ruler_weight_value_target);
                            values.put("weightRecord",tester.getWeight());
                            db.update("UserInfo", values, "email = ?", new String[] {tester.getName()});
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    func();
                    dismiss();
                }
            });
        }

        final Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 显示PopupWindow
     */
    private void show(View v) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        }
        setWindowAlpa(true);
    }

    /**
     * 消失PopupWindow
     */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * 动态设置Activity背景透明度
     *
     * @param isopen
     */
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = ((Activity) mContext).getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }

    /**
     * 点击事件选择回调
     */
    public interface onPopupWindowItemClickListener {
        void onItemClick(int position);
    }


    public static interface onCallBackListener {
        public void update();
    }

    public void setCallBackListener(onCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    private onCallBackListener callBackListener;

    private void func() {

        if (callBackListener != null) {

            callBackListener.update();
        }
    }
}
