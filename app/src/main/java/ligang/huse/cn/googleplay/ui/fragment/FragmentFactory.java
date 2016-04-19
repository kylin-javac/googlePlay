package ligang.huse.cn.googleplay.ui.fragment;

import java.util.HashMap;

/**
 * FragmentFactory工厂类
 */
public class FragmentFactory {

    private static HashMap  <Integer,BaseFragment>maps=new HashMap<>();
    public static BaseFragment createFragment(int pos) {
          BaseFragment fragment = maps.get(pos);
        if(fragment==null) {
            switch (pos) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new sbujectFragment();
                    break;
                case 4:
                    fragment = new RecommenFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;
            }
            maps.put(pos,fragment);
        }

        return fragment;
    }


}
