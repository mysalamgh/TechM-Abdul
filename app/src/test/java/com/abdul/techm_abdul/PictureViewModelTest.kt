package com.abdul.techm_abdul

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.abdul.techm_abdul.models.Picture
import com.abdul.techm_abdul.viewmodels.PictureViewModel
import mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class PictureViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var pictureViewModel: PictureViewModel
    private val observerUserPictures: Observer<ArrayList<Picture>> = mock()
    private val observerSelectedPicture: Observer<Picture> = mock()

    @Captor
    private lateinit var captorUserPictures: ArgumentCaptor<ArrayList<Picture>>
    private val captorSelectedPicture = ArgumentCaptor.forClass(Picture::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        pictureViewModel = PictureViewModel()
        prepareFakePictures()
        pictureViewModel.getUserPictures(1).observeForever(observerUserPictures)
        pictureViewModel.getSelectedPicture().observeForever(observerSelectedPicture)
    }

    @Test
    fun getUserPictures_ShouldReturnTrueForUserPictures() {
        var albumIdCheck = true
        captorUserPictures.run {
            verify(observerUserPictures, times(1)).onChanged(capture())
            for (pic in value) {
                if (pic.albumId != 1) {
                    albumIdCheck = false
                }
            }
            assertEquals(true, albumIdCheck)
        }
    }

    @Test
    fun selectPicture_ShouldReturnSelectedPicture() {
        val expectedPicture = pictureViewModel.getPictures().value?.get(3)
        expectedPicture?.let { pictureViewModel.selectPicture(it) }
        captorSelectedPicture.run {
            verify(observerSelectedPicture, times(1)).onChanged(capture())
            assertEquals(expectedPicture, value)
        }
    }

    private fun prepareFakePictures() {
        var pics: ArrayList<Picture> = ArrayList()
        for (i in 1..10) {
            pics.add(Picture((i % 2) + 1, i, "Title", "url", "thumbnailUrl"))
        }
        pictureViewModel.setPictures(pics)
    }
}