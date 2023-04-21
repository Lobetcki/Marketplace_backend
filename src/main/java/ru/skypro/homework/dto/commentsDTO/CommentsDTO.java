package ru.skypro.homework.dto.commentsDTO;

import lombok.Getter;
import lombok.Setter;
import ru.skypro.homework.model.Comments;

import java.time.Instant;

@Getter
@Setter
public class CommentsDTO {

    public Long author;
    public String authorImage;
    public String authorFirstName;
    public Instant createdAt;
    public Long pk;
    public String text;

    public static CommentsDTO fromCommentsDTO(Comments comments) {
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setAuthor(comments.getUsers().getId());
        commentsDTO.setAuthorImage("/ads/image/"
                + comments.getUsers().getUserImage().getId());
        commentsDTO.setAuthorFirstName(comments.getUsers().getFirstName());
        commentsDTO.setCreatedAt(comments.getCreatedAtDate());
        commentsDTO.setPk(comments.getId());
        commentsDTO.setText(comments.getText());
        return commentsDTO;
    }

    public Comments toComments() {
        Comments comments = new Comments();
        comments.setId(this.getPk());
        comments.setCreatedAtDate(this.getCreatedAt());
        comments.setText(this.getText());
        return comments;
    }
}
