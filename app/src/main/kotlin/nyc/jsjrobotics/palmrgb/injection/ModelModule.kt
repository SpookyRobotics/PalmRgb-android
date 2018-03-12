package nyc.jsjrobotics.palmrgb.injection

import dagger.Module
import dagger.Provides
import nyc.jsjrobotics.palmrgb.Application
import nyc.jsjrobotics.palmrgb.createFrame.CreateFrameDialogModel
import nyc.jsjrobotics.palmrgb.database.AppDatabase


@Module
class ModelModule {
    @Provides
    internal fun provideCreateFrameModel() : CreateFrameDialogModel = CreateFrameDialogModel.instance()


}