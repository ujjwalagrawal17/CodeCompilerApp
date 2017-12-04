package sharedcode.turboeditor.compiler.presenter;

import sharedcode.turboeditor.compiler.CompilerCallback;
import sharedcode.turboeditor.compiler.data.CompilerResponse;
import sharedcode.turboeditor.compiler.model.CompilerHelper;
import sharedcode.turboeditor.compiler.view.CompilerView;

/**
 * Created by ujjwal on 4/12/17.
 */

public class CompilerPresenterImpl implements CompilerPresenter {
	private CompilerView compilerView;
	private CompilerHelper compilerHelper;

	public CompilerPresenterImpl(CompilerView compilerView, CompilerHelper compilerHelper) {
		this.compilerView = compilerView;
		this.compilerHelper = compilerHelper;
	}

	@Override
	public void compileCode(int language, String code, String stdin) {
		compilerHelper.compileCode(language, code, stdin, new CompilerCallback() {
			@Override
			public void onSuccess(CompilerResponse compilerResponse) {

				compilerView.showOutput(compilerResponse.getOutput());
				compilerView.showRunTime(compilerResponse.getTime());
				compilerView.showError(compilerResponse.getErrors());
			}

			@Override
			public void onFailure() {
				compilerView.showError("Unable to connect to server. . . .");
			}
		});
	}
}
