package com.asknilesh.di

import android.app.Application
import androidx.room.Room
import com.asknilesh.noteapplication.feature_note.data.data_source.NoteDataBase
import com.asknilesh.noteapplication.feature_note.data.repository.NoteRepositoryImpl
import com.asknilesh.noteapplication.feature_note.domain.repository.NoteRepository
import com.asknilesh.noteapplication.feature_note.domain.use_case.AddNoteUseCase
import com.asknilesh.noteapplication.feature_note.domain.use_case.DeleteNoteUseCase
import com.asknilesh.noteapplication.feature_note.domain.use_case.GetNotesUseCase
import com.asknilesh.noteapplication.feature_note.domain.use_case.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  @Singleton
  fun provideNotesDataBase(app: Application): NoteDataBase {
    return Room.databaseBuilder(app, NoteDataBase::class.java, NoteDataBase.DATABASE_NAME).build()
  }

  @Provides
  @Singleton
  fun provideNotesRepository(db: NoteDataBase): NoteRepository {
    return NoteRepositoryImpl(db.noteDao)
  }

  @Provides
  @Singleton
  fun provideNotesUseCases(repository: NoteRepository): NotesUseCases {
    return NotesUseCases(
      getNotesUseCase = GetNotesUseCase(repository),
      deleteNoteUseCase = DeleteNoteUseCase(repository),
      addNoteUseCase = AddNoteUseCase(repository)
    )
  }
}