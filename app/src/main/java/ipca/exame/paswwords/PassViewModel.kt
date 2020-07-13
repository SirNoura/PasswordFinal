package ipca.exame.paswwords

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PassViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PassRepository

    val allWords: LiveData<List<Pass>>

    init {
        val passdDao = WordRoomDatabase.getDatabase(application, viewModelScope).passDao()
        repository = PassRepository(passdDao)
        allWords = repository.allWords
    }

    fun insert(pass: Pass) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(pass)
    }


}