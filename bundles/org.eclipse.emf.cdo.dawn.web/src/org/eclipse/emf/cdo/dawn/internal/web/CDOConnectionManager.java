package org.eclipse.emf.cdo.dawn.internal.web;

import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.session.CDOSession;

import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.io.IOUtil;

import java.io.Closeable;

public enum CDOConnectionManager implements Closeable
{
  INSTANCE;

  public static CDOConnectionManager getInstance()
  {
    return INSTANCE;
  }

  private static final boolean CLOSE_SESSION_AFTER_ALL_RELEASED = false;

  private final Object sessionLock = new Object();

  private int sessionCount = 0;

  private CDOSession session;

  public CDOSession acquireSession()
  {
    synchronized (sessionLock)
    {
      if (session == null)
      {
        session = createSession();
        sessionCount = 0;
      }
      sessionCount++;
      return session;
    }
  }

  public void releaseSession()
  {
    synchronized (sessionLock)
    {
      if (sessionCount > 0)
      {
        sessionCount--;
      }
      else
      {
        OM.LOG.warn("Tried to release more sessions than acquired.");
      }
      if (sessionCount < 0 && CLOSE_SESSION_AFTER_ALL_RELEASED)
      {
        close();
      }
    }
  }

  public void close()
  {
    synchronized (sessionLock)
    {
      IOUtil.closeSilent(session);
      session = null;
      sessionCount = 0;
    }
  }

  private static CDOSession createSession()
  {
    String connectionString = String.format("%s:%d", DawnServerConfig.getInstance().getCDOHostname(),
        DawnServerConfig.getInstance().getCDOPort());
    IConnector connector = TCPUtil.getConnector(IPluginContainer.INSTANCE, connectionString);

    CDONet4jSessionConfiguration sessionConfiguration = CDONet4jUtil.createNet4jSessionConfiguration();
    sessionConfiguration.setConnector(connector);
    sessionConfiguration.setRepositoryName(DawnServerConfig.getInstance().getCDORepository());
    return sessionConfiguration.openNet4jSession();
  }

}
