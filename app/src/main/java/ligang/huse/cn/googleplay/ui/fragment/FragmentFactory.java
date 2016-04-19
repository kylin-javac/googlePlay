package ligang.huse.cn.googleplay.ui.fragment;

import java.util.HashMap;

/**
 * FragmentFactory工厂类
 */
public class FragmentFactory {

    private static BaseFragment baseFragment;

    public static BaseFragment createFragment(int pos) {
        baseFragment = null;
        HashMap<Integer,BaseFragment>maps=new HashMap<>();
        BaseFragment fragment = maps.get(pos);
        if(fragment==null) {
            switch (pos) {
                case 0:
                    FragmentFactory.baseFragment = new HomeFragment();
                    break;
                case 1:
                    FragmentFactory.baseFragment = new AppFragment();
                    break;
                case 2:
                    FragmentFactory.baseFragment = new GameFragment();
                    break;
                case 3:
                    FragmentFactory.baseFragment = new sbujectFragment();
                    break;
                case 4:
                    FragmentFactory.baseFragment = new RecommenFragment();
                    break;
                case 5:
                    FragmentFactory.baseFragment = new CategoryFragment();
                    break;
                case 6:
                    FragmentFactory.baseFragment = new HotFragment();
                    break;
            }
            maps.put(pos,baseFragment);
        }

        return FragmentFactory.baseFragment;
    }


}
