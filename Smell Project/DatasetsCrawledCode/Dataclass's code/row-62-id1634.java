public class AnnotPage extends ContactsDisplayPage
{
 @SpringBean
 private ContactDao dao;


 @Override
 protected ContactDataProvider getDataProvider()
	{
 return new ProxyDataProvider(dao);
	}
}