package com.fdsdidgzr.pruebacronet;

import android.util.Log;

import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class PruebaURLRequestCallback extends UrlRequest.Callback {
    private final ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
    private final WritableByteChannel mWbc = Channels.newChannel(mBaos);

    @Override
    public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) {s
        if (true) {
            request.followRedirect();
        } else {
            request.cancel();
        }
    }

    @Override
    public void onResponseStarted(UrlRequest request, UrlResponseInfo responseInfo) {
        request.read(ByteBuffer.allocateDirect(102400));
        int httpStatusCode = responseInfo.getHttpStatusCode();
        Util.logI("httpStatusCode: "+httpStatusCode);
    }

    @Override
    public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {
        try {
            byteBuffer.flip();
            CharBuffer cb = byteBuffer.asCharBuffer();
            Util.logI(Charset.forName(StandardCharsets.UTF_8.name()).decode(byteBuffer).toString());
        } catch (Exception e) {
            Util.logE("IOException during ByteBuffer read. Details: " + e);
        }

        byteBuffer.clear();
        request.read(byteBuffer);
    }

    @Override
    public void onSucceeded(UrlRequest request, UrlResponseInfo info) {s
        Util.logI("Request exitoso ");
    }

    @Override
    public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
        Util.logI("Request failed. ");
    }
}