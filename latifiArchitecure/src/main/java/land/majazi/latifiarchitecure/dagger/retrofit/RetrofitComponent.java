package land.majazi.latifiarchitecure.dagger.retrofit;

import dagger.Component;
import land.majazi.latifiarchitecure.dagger.DaggerScope;
import retrofit2.Retrofit;

@DaggerScope
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    Retrofit getRetrofit();
}
