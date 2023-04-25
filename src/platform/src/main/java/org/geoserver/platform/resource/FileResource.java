package org.geoserver.platform.resource;

import java.io.File;

public abstract class FileResource implements Resource {

    final File file;

    protected FileResource(File file) {
        this.file = file;
    }

    /**
     * LockProvider used during {@link Resource#out()}.
     *
     * <p>Client code that insists on using {@link Resource#file()} can do us using:
     *
     * <pre><code>
     * Resource resource = resoures.get( "example.txt" );
     * Lock lock = resources.getLockProvider().acquire( resource.path() );
     * try {
     *    File file = resoruce.file();
     *    ..
     * }
     * finally {
     *    lock.release();
     * }
     * </code></pre>
     *
     * @return LockProvider used for {@link Resource#out}
     */
    public abstract LockProvider getLockProvider();

    @Override
    public Lock lock() {
        return getLockProvider().acquire(path());
    }

    protected abstract ResourceNotificationDispatcher getResourceNotificationDispatcher();

    @Override
    public void addListener(ResourceListener listener) {
        getResourceNotificationDispatcher().addListener(path(), listener);
    }

    @Override
    public void removeListener(ResourceListener listener) {
        getResourceNotificationDispatcher().removeListener(path(), listener);
    }
}
