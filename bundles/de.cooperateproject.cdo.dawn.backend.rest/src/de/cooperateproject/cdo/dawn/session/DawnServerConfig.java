package de.cooperateproject.cdo.dawn.session;

public enum DawnServerConfig
{
  INSTANCE;

  public static DawnServerConfig getInstance()
  {
    return INSTANCE;
  }

  private final String cdoHostname;

  private final Integer cdoPort;

  private final String cdoRepository;

  private DawnServerConfig()
  {
    cdoHostname = System.getProperty("de.cooperateproject.dawnweb.cdo.host", "localhost");
    cdoPort = Integer.getInteger("de.cooperateproject.dawnweb.cdo.port", 2036);
    cdoRepository = System.getProperty("de.cooperateproject.dawnweb.cdo.repo", "repo1");
  }

  public String getCDORepository()
  {
    return cdoRepository;
  }

  public String getCDOHostname()
  {
    return cdoHostname;
  }

  public Integer getCDOPort()
  {
    return cdoPort;
  }
}
