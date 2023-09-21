package com.hieunv.mvvmarch.base.repository

import com.hieunv.mvvmarch.base.repository.auth.AuthRepository
import com.hieunv.mvvmarch.base.repository.auth.AuthRepositoryImpl
import com.hieunv.mvvmarch.base.repository.post.PostRepository
import com.hieunv.mvvmarch.base.repository.post.PostRepositoryImpl
import com.hieunv.mvvmarch.base.repository.user.UserRepository
import com.hieunv.mvvmarch.base.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    // Makes Dagger provide AuthRepositoryImpl when a AuthRepository type is requested
    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    // Makes Dagger provide UserRepositoryImpl when a UserRepository type is requested
    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    // Makes Dagger provide PostRepositoryImpl when a PostRepository type is requested
    @Binds
    abstract fun providePostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

}
