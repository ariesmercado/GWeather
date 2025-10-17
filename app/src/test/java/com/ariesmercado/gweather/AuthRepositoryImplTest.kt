package com.ariesmercado.gweather

import com.ariesmercado.gweather.common.constant.Constants
import com.ariesmercado.gweather.data.repository.AuthRepositoryImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class AuthRepositoryImplTest {

    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockAuthResult: AuthResult

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    private lateinit var repository: AuthRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = AuthRepositoryImpl(mockAuth)
    }

    @Test
    fun `signIn returns AuthResult on success`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "123456"

        val listenerCaptor = ArgumentCaptor.forClass(OnCompleteListener::class.java) as ArgumentCaptor<OnCompleteListener<AuthResult>>

        `when`(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockTask.result).thenReturn(mockAuthResult)

        // Act
        var result: AuthResult? = null
        val job = launch {
            result = repository.signIn(email, password)
        }

        // Trigger completion manually
        verify(mockTask).addOnCompleteListener(listenerCaptor.capture())
        listenerCaptor.value.onComplete(mockTask)
        job.join()

        // Assert
        assertNotNull(result)
        assertEquals(mockAuthResult, result)
    }

    @Test
    fun `signIn throws exception on failure`() = runTest {
        val email = "fail@example.com"
        val password = "wrongpass"
        val exception = Exception("Invalid credentials")

        val listenerCaptor = ArgumentCaptor.forClass(OnCompleteListener::class.java) as ArgumentCaptor<OnCompleteListener<AuthResult>>

        `when`(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(exception)

        val job = launch {
            try {
                repository.signIn(email, password)
                fail("Expected exception not thrown")
            } catch (e: Exception) {
                assertEquals(exception, e)
            }
        }

        verify(mockTask).addOnCompleteListener(listenerCaptor.capture())
        listenerCaptor.value.onComplete(mockTask)
        job.join()
    }

    @Test
    fun `signUp returns AuthResult on success`() = runTest {
        val email = "new@example.com"
        val password = "123456"

        val listenerCaptor = ArgumentCaptor.forClass(OnCompleteListener::class.java) as ArgumentCaptor<OnCompleteListener<AuthResult>>

        `when`(mockAuth.createUserWithEmailAndPassword(email, password)).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockTask.result).thenReturn(mockAuthResult)

        var result: AuthResult? = null
        val job = launch {
            result = repository.signUp(email, password)
        }

        verify(mockTask).addOnCompleteListener(listenerCaptor.capture())
        listenerCaptor.value.onComplete(mockTask)
        job.join()

        assertNotNull(result)
        assertEquals(mockAuthResult, result)
    }

    @Test
    fun `signUp throws unknown error when exception is null`() = runTest {
        val email = "user@example.com"
        val password = "123456"

        val listenerCaptor = ArgumentCaptor.forClass(OnCompleteListener::class.java) as ArgumentCaptor<OnCompleteListener<AuthResult>>

        `when`(mockAuth.createUserWithEmailAndPassword(email, password)).thenReturn(mockTask)
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(null)

        val job = launch {
            try {
                repository.signUp(email, password)
                fail("Expected exception not thrown")
            } catch (e: Exception) {
                assertEquals(Constants.ERROR_UNKNOWN, e.message)
            }
        }

        verify(mockTask).addOnCompleteListener(listenerCaptor.capture())
        listenerCaptor.value.onComplete(mockTask)
        job.join()
    }
}
