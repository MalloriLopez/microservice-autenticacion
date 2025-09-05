package co.com.bancolombia.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolEntity {
    @Id
    @Column("rol_id")
    private Long rolId;
    private String nombre;
    private String descripcion;
}
