package nyc.jsjrobotics.palmrgb.injection;

import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import nyc.jsjrobotics.palmrgb.CreateFrameFragment;
import nyc.jsjrobotics.palmrgb.injection.androidSubcomponents.CreateFrameFragmentSubcomponent;

@Module(subcomponents = {
        CreateFrameFragmentSubcomponent.class
})

abstract class FragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(CreateFrameFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment> bindAddFragmentInjectorFactory(CreateFrameFragmentSubcomponent.Builder builder);
}
