package sharedcode.turboeditor.compiler;


import sharedcode.turboeditor.compiler.data.CompilerResponse;

/**
 * Created by ramya on 17/10/16.
 */

public interface CompilerCallback {
    void onSuccess(CompilerResponse compilerResponse);
    void onFailure();
}
