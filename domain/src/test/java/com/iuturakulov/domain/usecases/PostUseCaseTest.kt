package com.iuturakulov.domain.usecases

import com.iuturakulov.domain.entities.PostEntity
import com.iuturakulov.domain.entities.Comment
import com.iuturakulov.domain.entities.UserEntity
import com.iuturakulov.domain.repositories.PostRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class PostUseCaseTest {

    @Mock
    lateinit var postRepository: PostRepository

    private lateinit var postUseCase: PostUseCase

    private val testUser = UserEntity(
        id = "1",
        name = "John Doe",
        email = "john.doe@example.com",
        profileImageUrl = "https://example.com/john_doe.jpg",
        university = "XYZ University",
        department = "Computer Science"
    )

    private val testComment = Comment(
        id = "1",
        postId = "1",
        userId = "1",
        content = "This is a test comment",
        timestamp = System.currentTimeMillis()
    )

    private val testPost = PostEntity(
        id = "1",
        author = testUser,
        content = "This is a test post",
        mediaLink = "https://example.com/media.jpg",
        timestamp = Date(),
        likes = 10,
        comments = listOf(testComment)
    )

    @Test
    fun `getPost should call corresponding method in repository`() = runBlocking {
        postUseCase = PostUseCase(postRepository)
        val postId = "1"
        postUseCase.getPost(postId)
        verify(postRepository).getPost(postId)
    }

    @Test
    fun `getPosts should call corresponding method in repository`() = runBlocking {
        postUseCase = PostUseCase(postRepository)
        postUseCase.getPosts()
        verify(postRepository).getPosts()
    }
}
