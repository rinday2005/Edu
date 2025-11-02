/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ArticalDAO;

/**
 *
 * @author ADMIN
 */
import java.util.List;
import java.util.UUID;
import model.Article;

public interface IArticle {
      void create(Article a);                     // C
    UUID  update(Article a);                     // U
    int  updateStatus(UUID articleId, String status);
    int  deleteById(UUID articleId);            // D
    Article findById(UUID articleId);           // R1
    List<Article> findAll();                    // R2
    public List<Article> findAllWithStats(); //lất thống kê từ Article
    public int increaseViewCount(UUID articleId, UUID viewerId, UUID authorId);
}
