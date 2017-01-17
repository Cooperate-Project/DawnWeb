package org.eclipse.emf.cdo.dawn.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The HttpServlet to serve logging requests.
 *
 * @author Shengjia Feng, Stephan Seifermann
 */
public class LogActionServlet extends HttpServlet
{
  private static final long serialVersionUID = 2L;

  private static final String LOG_FILE_URL = System.getProperty("de.cooperateproject.dawnweb.log", "~/Downloads/");

  private static final String LOG_FILE_NAME = "DawnAccessibleEditorLog_";

  private static final String LOG_FILE_TYPE = ".csv";

  private static final Set<Path> LOCKS = new HashSet<Path>();

  /**
   * Locking the a specific path.
   *
   * @param lockID
   *          The path to be locked.
   */
  private static void waitFor(Path lockID) throws InterruptedException
  {
    synchronized (LOCKS)
    {
      while (LOCKS.contains(lockID))
      {
        LOCKS.wait();
      }
      LOCKS.add(lockID);
    }
  }

  /**
   * Releases the lock on one path.
   *
   * @param lockID
   *          The path off which the lock should be released.
   */
  private static void free(Path lockID)
  {
    synchronized (LOCKS)
    {
      LOCKS.remove(lockID);
      LOCKS.notifyAll();
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    String message = request.getParameter("message");
    String code = request.getParameter("code");
    String fileName = "";

    if (code != null)
    {
      fileName = code + "_" + LOG_FILE_NAME;
    }
    else
    {
      fileName = LOG_FILE_NAME;
    }

    // Check if file exists
    String pathToLogFile = LOG_FILE_URL + fileName + request.getSession().getId() + LOG_FILE_TYPE;
    Path path = Paths.get(pathToLogFile);

    try
    {
      waitFor(path);
      File f = new File(pathToLogFile);
      if (!f.exists() || f.isDirectory())
      {
        Files.createDirectories(path.getParent());
        Files.createFile(path);
      }

      // Write into file

      List<String> lines = Arrays.asList(message);
      Files.write(path, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
      response.setStatus(HttpServletResponse.SC_OK);
    }
    catch (Exception e)
    {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
    finally
    {
      free(path);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    doGet(request, response);
  }
}
