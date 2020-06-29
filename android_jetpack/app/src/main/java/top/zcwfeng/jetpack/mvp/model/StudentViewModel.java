package top.zcwfeng.jetpack.mvp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import top.zcwfeng.jetpack.mvp.db.Student;
import top.zcwfeng.jetpack.mvp.db.StudentRepository;

public class StudentViewModel extends AndroidViewModel {
    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application);

    }

    private StudentRepository studentRepository;


    public void insert(Student... students) {
        studentRepository.insert(students);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public void update(Student student) {
        studentRepository.update(student);
    }

    public List<Student> getAll() {
        return studentRepository.getAll();
    }


    public LiveData<List<Student>> getAllLiveDataStudent() {
        return studentRepository.getLiveDataAllStudent();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
