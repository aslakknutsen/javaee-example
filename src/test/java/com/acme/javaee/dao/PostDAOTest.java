package com.acme.javaee.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.javaee.domain.Model;
import com.acme.javaee.domain.Post;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.ROLLBACK)
public class PostDAOTest
{
  private static final Logger LOG = Logger.getLogger(PostDAOTest.class
                                      .getName());

  @Deployment
  public static Archive<?> createDeployment()
  {
    final Archive<?> archive = ShrinkWrap.create(JavaArchive.class)
        .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
        .addAsManifestResource("persistence.xml")
        .addClasses(DAO.class, Model.class, Post.class, PostDAO.class);

    LOG.info("DEPLOYMENT: " + archive.toString(true));

    return archive;
  }

  @EJB
  private PostDAO postDAO;

  @Test
  public void testCreate1()
  {
    LOG.info("testCreate1()");

    Assert.assertNotNull(postDAO.create("title1", "content1", 1));
    Assert.assertNotNull(postDAO.create("title2", "content2", 2));
    Assert.assertNotNull(postDAO.create("title3", "content3", 3));
  }

  @Test(expected = EJBException.class)
  public void testDelete1()
  {
    LOG.info("testDelete1()");

    Assert.assertEquals(0, postDAO.findTotal());

    postDAO.delete((Post) null);
  }

  @Test(expected = EJBException.class)
  public void testDelete2()
  {
    LOG.info("testDelete2()");

    Assert.assertEquals(0, postDAO.findTotal());

    postDAO.delete(-1L);
  }

  @Test
  public void testDelete3()
  {
    LOG.info("testDelete3()");

    Assert.assertEquals(0, postDAO.findTotal());

    postDAO.delete(postDAO.create("title1", "content1", 1));
  }

  @Test
  public void testFindAll1()
  {
    LOG.info("testFindAll1()");

    Assert.assertEquals(0, postDAO.findTotal());

    final List<Post> result = postDAO.findAll();

    Assert.assertNotNull(result);
    Assert.assertEquals(0, result.size());
  }

  @Test
  public void testFindAll2()
  {
    LOG.info("testFindAll2()");

    Assert.assertEquals(0, postDAO.findTotal());

    Assert.assertNotNull(postDAO.create("title1", "content1", 1));
    Assert.assertNotNull(postDAO.create("title2", "content2", 2));
    Assert.assertNotNull(postDAO.create("title3", "content3", 3));

    final List<Post> result = postDAO.findAll();

    Assert.assertNotNull(result);
    Assert.assertEquals(3, result.size());
  }

  @Test
  public void testFindById1()
  {
    LOG.info("testFindById1()");

    Assert.assertEquals(0, postDAO.findTotal());

    Assert.assertNull(postDAO.findById(-1L));
  }

  @Test
  public void testInject1()
  {
    LOG.info("testInject1()");

    Assert.assertNotNull(postDAO);
  }

  @Test(expected = EJBException.class)
  public void testUpdate1()
  {
    LOG.info("testUpdate1()");

    Assert.assertEquals(0, postDAO.findTotal());

    postDAO.update((Post) null);
  }

  @Test(expected = EJBException.class)
  public void testUpdate2()
  {
    LOG.info("testUpdate2()");

    Assert.assertEquals(0, postDAO.findTotal());

    postDAO.update(-1L, -1L, (String) null, (String) null);
  }
}
