package top.zcwfeng.android_okhttp;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ContentRequestBody extends RequestBody {

    protected Listener listener;
    private RequestBody delegate;
    private ContingSink mSink;

    public ContentRequestBody(RequestBody delegate, Listener listener){
        this.delegate = delegate;
        this.listener = listener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {
        mSink = new ContingSink(bufferedSink);
        BufferedSink mBufferSink = Okio.buffer(mSink);
        delegate.writeTo(mBufferSink
        );
        mBufferSink.flush();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return delegate.contentLength();
        } catch (IOException e) {
            return -1;
        }
    }

    protected final class ContingSink extends ForwardingSink {
        private long byteWritrd;

        public ContingSink(@NotNull Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(@NotNull Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritrd += byteCount;
            listener.onRequestProgress(byteWritrd,contentLength());
        }
    }

    public static interface Listener{
        void onRequestProgress(long byteWritrd,long contentLength);
    }

}
