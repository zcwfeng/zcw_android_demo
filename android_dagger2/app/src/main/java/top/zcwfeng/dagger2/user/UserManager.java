package top.zcwfeng.dagger2;

public class UserManager {
    private ApiService apiService;
    private UserStore userStore;

    public UserManager(ApiService apiService,UserStore userStore){
        this.apiService = apiService;
        this.userStore = userStore;
    }

    public void register(){
        apiService.register();
        userStore.register();
    }
}
