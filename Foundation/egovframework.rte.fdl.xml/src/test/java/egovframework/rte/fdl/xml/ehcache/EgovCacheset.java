package egovframework.rte.fdl.xml.ehcache;

import egovframework.rte.fdl.xml.EgovConcreteDOMFactory;
import egovframework.rte.fdl.xml.EgovConcreteSAXFactory;

public class EgovCacheset
{
	private EgovConcreteDOMFactory domconcrete;
	private EgovConcreteSAXFactory saxconcrete;
	
	public void setDomconcrete(EgovConcreteDOMFactory domconcrete)
	{
		this.domconcrete = domconcrete;
	}
	
	public EgovConcreteDOMFactory getDomconcrete()
	 {
		 return domconcrete;
	 }
	public void setSaxconcrete(EgovConcreteSAXFactory saxconcrete)
	{
		this.saxconcrete = saxconcrete;
	}
	
	public EgovConcreteSAXFactory getSaxconcrete()
	 {
		 return saxconcrete;
	 }
}