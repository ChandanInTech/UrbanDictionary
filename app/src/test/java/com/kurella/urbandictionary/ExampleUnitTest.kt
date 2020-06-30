package com.kurella.urbandictionary

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kurella.urbandictionary.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ExampleUnitTest {

    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        mainViewModel = spyk(MainViewModel())
    }

    @Test
    fun checkIfFABColorChanged(){
        mainViewModel.updateIsUpVote()
        mainViewModel.getFabColorLiveData().observeForever {
            assertEquals(it, R.color.colorRed)
        }
    }
}
