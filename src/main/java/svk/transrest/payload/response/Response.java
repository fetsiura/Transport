package svk.transrest.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Purpose:
 * The Response class represents a generic response structure for API endpoints, encapsulating the data, metadata, and error handling.
 *
 * @param <T>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private List<T> content;
    private Integer page;
    private Long totalElements;
    private Integer totalPages;
    private Boolean last;
    private Object errors;
    private String info;
}