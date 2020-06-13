package com.abdul.techm_abdul

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.abdul.techm_abdul.models.Address
import com.abdul.techm_abdul.models.Company
import com.abdul.techm_abdul.models.Geo
import com.abdul.techm_abdul.models.User
import com.abdul.techm_abdul.viewmodels.UserViewModel
import mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class UserViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var userViewModel: UserViewModel

    private val observer: Observer<User> = mock()

    private var users: ArrayList<User> = ArrayList()

    @Before
    fun setUp() {
        prepareFakeUsers()
        userViewModel = UserViewModel()
        userViewModel.getSelectedUser().observeForever(observer)
    }

    @Test
    fun selectedUser_ShouldReturnSelectedUser() {
        val expectedUser = users[5]
        userViewModel.selectUser(expectedUser)
        val captor = ArgumentCaptor.forClass(User::class.java)
        captor.run {
            verify(observer, times(1)).onChanged(capture())
            assertEquals(expectedUser, value)
        }
    }

    fun prepareFakeUsers() {
        for (i in 1..10) {
            users.add(
                User(
                    i,
                    "Abdul",
                    "abdul",
                    "ab@y.com",
                    Address("13 Shreeve Rd", "suite", "Perth", "1234", Geo(0.0, 0.0)),
                    "0400123456",
                    "www.aaa.com",
                    Company("ABC Ltd", "AAA", "BBB")
                )
            )
        }
    }

}