/***************************************************************************
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.pop.internal.ui.actions;

import org.eclipse.net4j.pop.impl.StreamManagerImpl;
import org.eclipse.net4j.util.WrappedException;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.mylyn.tasks.core.IRepositoryManager;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.ITaskDataManager;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.ui.TasksUi;

import java.util.List;

/**
 * @author Eike Stepper
 */
public class TaskPropertyTester extends PropertyTester
{
  public static final IRepositoryManager REPOSITORY_MANAGER = TasksUi.getRepositoryManager();

  public static final ITaskDataManager TASK_DATA_MANAGER = TasksUi.getTaskDataManager();

  public TaskPropertyTester()
  {
  }

  public boolean test(Object receiver, String property, Object[] args, Object expectedValue)
  {
    if (expectedValue == null)
    {
      expectedValue = Boolean.TRUE;
    }

    if (receiver instanceof ITask && expectedValue instanceof Boolean)
    {
      ITask task = (ITask)receiver;
      boolean value = (Boolean)expectedValue;
      if ("hasPop".equals(property))
      {
        return hasPop(task) == value;
      }
      else if ("hasMaintenanceStream".equals(property))
      {
        return hasMainenanceStream(task, value);
      }
      else if ("hasTaskStream".equals(property))
      {
        return hasTaskStream(task) == value;
      }
    }

    return false;
  }

  public static boolean hasPop(ITask task)
  {
    return parseOperations(task, StreamManagerImpl.PREFIX_CREATED_POP);
  }

  public static boolean hasMainenanceStream(ITask task, boolean value)
  {
    return parseOperations(task, StreamManagerImpl.PREFIX_CREATED_MAINTENANCE_STREAM) == value;
  }

  public static boolean hasTaskStream(ITask task)
  {
    return parseOperations(task, StreamManagerImpl.PREFIX_CREATED_TASK_STREAM);
  }

  @SuppressWarnings("restriction")
  private static boolean parseOperations(ITask task, String prefix)
  {
    try
    {
      String marker = StreamManagerImpl.PREFIX_OPERATION + prefix;
      if (task instanceof org.eclipse.mylyn.internal.tasks.core.AbstractTask)
      {
        String notes = ((org.eclipse.mylyn.internal.tasks.core.AbstractTask)task).getNotes();
        if (notes.contains(marker))
        {
          return true;
        }
      }

      TaskRepository repository = REPOSITORY_MANAGER.getRepository(task.getConnectorKind(), task.getRepositoryUrl());
      TaskData taskData = TASK_DATA_MANAGER.getTaskData(repository, task.getTaskId());

      if (taskData != null)
      {
        TaskAttributeMapper attributeMapper = taskData.getAttributeMapper();
        String summary = taskData.getRoot().getMappedAttribute(TaskAttribute.SUMMARY).getValue();
        if (summary.contains(marker))
        {
          return true;
        }

        String description = taskData.getRoot().getMappedAttribute(TaskAttribute.DESCRIPTION).getValue();
        if (description.contains(marker))
        {
          return true;
        }

        List<TaskAttribute> comments = attributeMapper.getAttributesByType(taskData, TaskAttribute.TYPE_COMMENT);
        for (TaskAttribute commentAttribute : comments)
        {
          String text = commentAttribute.getMappedAttribute(TaskAttribute.COMMENT_TEXT).getValue();
          if (text.contains(marker))
          {
            return true;
          }
        }
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      throw WrappedException.wrap(ex);
    }

    return false;
  }
}
