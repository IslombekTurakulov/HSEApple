package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.CourseEntity
import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.repositories.CourseRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class CourseUseCaseTest {

    @Mock
    lateinit var courseRepository: CourseRepository

    private lateinit var courseUseCase: CourseUseCase

    private val testUser = UserEntity(
        id = "1",
        name = "John Doe",
        email = "john.doe@example.com",
        profileImageUrl = "https://example.com/john_doe.jpg",
        university = "XYZ University",
        department = "Computer Science"
    )

    private val testCourse = CourseEntity(
        id = "1",
        title = "Test Course",
        description = "This is a test course",
        instructor = testUser,
        startDate = Date(),
        endDate = Date()
    )

    @Test
    fun `getCourse should call corresponding method in repository`() = runBlocking {
        courseUseCase = CourseUseCase(courseRepository)
        val courseId = "1"
        courseUseCase.getCourse(courseId)
        verify(courseRepository).getCourse(courseId)
    }

    @Test
    fun `getCourses should call corresponding method in repository`() = runBlocking {
        courseUseCase = CourseUseCase(courseRepository)
        courseUseCase.getCourses()
        verify(courseRepository).getCourses()
    }

    @Test
    fun `createCourse should call corresponding method in repository`() = runBlocking {
        courseUseCase = CourseUseCase(courseRepository)
        courseUseCase.createCourse(testCourse)
        verify(courseRepository).createCourse(testCourse)
    }

    @Test
    fun `updateCourse should call corresponding method in repository`() = runBlocking {
        courseUseCase = CourseUseCase(courseRepository)
        courseUseCase.updateCourse(testCourse)
        verify(courseRepository).updateCourse(testCourse)
    }

    @Test
    fun `deleteCourse should call corresponding method in repository`() = runBlocking {
        courseUseCase = CourseUseCase(courseRepository)
        val courseId = "1"
        courseUseCase.deleteCourse(courseId)
        verify(courseRepository).deleteCourse(courseId)
    }
}
