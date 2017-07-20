package cheqfast.gfin.wasys.com.br.coleta.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import cheqfast.gfin.wasys.com.br.coleta.fragment.ImagemFragment;
import cheqfast.gfin.wasys.com.br.coleta.model.ImagemModel;

/**
 * Created by pascke on 26/06/17.
 */

public class ImagePageAdapter extends FragmentStatePagerAdapter {

    private List<ImagemModel> mModels;

    public ImagePageAdapter(FragmentManager fm, List<ImagemModel> models) {
        super(fm);
        mModels = models;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (CollectionUtils.isNotEmpty(mModels)) {
            ImagemModel model = mModels.get(position);
            fragment = ImagemFragment.newInstance(model);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return CollectionUtils.size(mModels);
    }
}