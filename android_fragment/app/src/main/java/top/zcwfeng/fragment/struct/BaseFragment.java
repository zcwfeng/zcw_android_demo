package top.zcwfeng.fragment.struct;

import android.content.Context;

import androidx.fragment.app.Fragment;

import top.zcwfeng.fragment.MainActivity;

public class BaseFragment extends Fragment {

    protected FunctionManager functionManager;

    protected MainActivity mBaseActivity;

    public void setFunctionManager(FunctionManager functionManager){
        this.functionManager = functionManager;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mBaseActivity = (MainActivity) context;
            mBaseActivity.setFunctionsForFragment(getTag());
        }

    }

}
