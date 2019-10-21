package velord.bnrg.photogallery.model

import androidx.paging.PositionalDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import velord.bnrg.photogallery.repository.FlickrRepository
import velord.bnrg.photogallery.repository.api.FlickrApi
import velord.bnrg.photogallery.utils.coroutineContext
import velord.bnrg.photogallery.utils.scope

private const val TAG = "PhotoDataSource"

class PhotoDataSource(val f: () -> List<Photo>) : PositionalDataSource<Photo>() {
    private val repository =
        FlickrRepository(FlickrApi.invoke())
    private var page = 1

    override fun loadInitial(params: LoadInitialParams,
                             callback: LoadInitialCallback<Photo>) {
        scope().launch {
            val data = f()
            callback.onResult(data, data.size)
        }
    }

    override fun loadRange(params: LoadRangeParams,
                           callback: LoadRangeCallback<Photo>) {
        scope().launch {
            val data = f()
            callback.onResult(data)
        }
    }

    fun cancelAllRequests() = coroutineContext().cancel()
}