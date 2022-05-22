package mumtaz.binar.noteapp.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager (context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "user")
    private val loginDS : DataStore<Preferences> = context.createDataStore(name = "login")

    //    ==============================================================================================
    companion object{

        val ID = preferencesKey<String>("id_user")
        val EMAIL = preferencesKey<String>("email_user")
        val USERNAME = preferencesKey<String>("username_user")
        val NAMA = preferencesKey<String>("nama_user")
        val TGLHR = preferencesKey<String>("tglhr_user")
        val ALAMAT = preferencesKey<String>("alamat_user")
        val LOGINSTATE = preferencesKey<String>("login_user")
    }

    //    ==============================================================================================
    suspend fun saveDataUser(id : String, email: String, username: String, nama : String, tglhr: String, alamat: String){
        dataStore.edit {
            it[ID] = id
            it[USERNAME] = username
            it[EMAIL] = email
            it[NAMA] = nama
            it[TGLHR] = tglhr
            it[ALAMAT] = alamat
        }
    }

    //    ==============================================================================================
    suspend fun saveDataLogin(login: String){
        loginDS.edit {
            it[LOGINSTATE] = login
        }
    }

    suspend fun deleteDataLogin(){
        loginDS.edit {
            it.clear()
        }
    }
    //    ==============================================================================================
    val userID : Flow<String> = dataStore.data.map {
        it[ID]?: ""
    }

    val userUsername : Flow<String> = dataStore.data.map {
        it[USERNAME]?: ""
    }

    val userEmail : Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }

    val userNama : Flow<String> = dataStore.data.map {
        it[NAMA] ?: ""
    }

    val userTGLHR : Flow<String> = dataStore.data.map {
        it[TGLHR] ?: ""
    }

    val userAlamat : Flow<String> = dataStore.data.map {
        it[ALAMAT] ?: ""
    }

    val userLogin: Flow<String> = loginDS.data.map {
        it[LOGINSTATE] ?: "false"
    }
// =================================================================================================
}