package nyc.jsjrobotics.palmrgb.injection

import dagger.Module
import dagger.Provides
import nyc.jsjrobotics.palmrgb.createFrame.CreateFrameDialogModel


@Module
class ModelModule {
    @Provides
    internal fun provideCreateFrameModel() : CreateFrameDialogModel = CreateFrameDialogModel.instance()

}