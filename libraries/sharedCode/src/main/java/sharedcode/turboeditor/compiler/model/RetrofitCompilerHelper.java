package sharedcode.turboeditor.compiler.model;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sharedcode.turboeditor.Urls;
import sharedcode.turboeditor.compiler.CompilerCallback;
import sharedcode.turboeditor.compiler.api.CompilerApi;
import sharedcode.turboeditor.compiler.data.CompilerRequest;
import sharedcode.turboeditor.compiler.data.CompilerResponse;

/**
 * Created by ujjwal on 4/12/17.
 */

public class RetrofitCompilerHelper implements CompilerHelper {

	private Call<CompilerResponse> call;

	@Override
	public void compileCode(int language, String code, String stdin, final CompilerCallback compilerCallback) {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
		Retrofit retrofit =new Retrofit.Builder().client(client).addConverterFactory(GsonConverterFactory.create()).baseUrl(Urls.BASE_URL).build();
		final CompilerApi compilerApi =retrofit.create(CompilerApi.class);
		CompilerRequest compilerRequest = new CompilerRequest(stdin,language,code);
		call = compilerApi.compileCode(compilerRequest);
		call.enqueue(new Callback<CompilerResponse>() {
			@Override
			public void onResponse(Call<CompilerResponse> call, Response<CompilerResponse> response) {
				compilerCallback.onSuccess(response.body());
			}

			@Override
			public void onFailure(Call<CompilerResponse> call, Throwable t) {
				compilerCallback.onFailure();
				t.printStackTrace();
			}
		});
	}
}
