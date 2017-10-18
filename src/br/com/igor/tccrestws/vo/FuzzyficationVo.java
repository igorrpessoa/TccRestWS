package br.com.igor.tccrestws.vo;

import java.util.List;

import br.com.igor.tccrestws.entity.Complemento;
import br.com.igor.tccrestws.entity.UsuarioAtividade;

/**
 * Created by Igor on 10/09/2017.
 */

public class FuzzyficationVo {
    private UsuarioAtividade usuarioAtividade;
    private List<Complemento> complementos;

    public UsuarioAtividade getUsuarioAtividade() {
        return usuarioAtividade;
    }

    public void setUsuarioAtividade(UsuarioAtividade usuarioAtividade) {
        this.usuarioAtividade = usuarioAtividade;
    }

    public List<Complemento> getComplementos() {
        return complementos;
    }

    public void setComplementos(List<Complemento> complementos) {
        this.complementos = complementos;
    }
}
