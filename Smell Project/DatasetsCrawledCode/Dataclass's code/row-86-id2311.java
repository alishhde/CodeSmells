public class ProjectList extends DataType implements Cloneable
{
 protected ArrayList list = new ArrayList();
 
 
 /**
     * add a project
     * @param pro
     */
 public void addProjectInfo(ProjectInfo pro)
    {
 list.add(pro);
    }
 
 /**
     * get project by index
     * @param index
     * @return
     */
 public ProjectInfo getProject(int index)
    {
 assert(index>=0 && index<list.size());
 return (ProjectInfo)list.get(index);
    }
 
 
 /**
     * get count
     * @return
     */
 public int getCount()
    {
 return list.size();
    }
 


 
}