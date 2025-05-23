package com.izatec.pay.core.cobranca.pagamento;

import com.izatec.pay.core.comum.PagamentoStatus;
import com.izatec.pay.infra.security.RequisicaoInfo;
import com.izatec.pay.infra.util.Filtros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.izatec.pay.infra.Atributos.*;
import static com.izatec.pay.infra.Atributos.STATUS;
@Service
public class PagamentoConsultaService {
    @Autowired
    private RequisicaoInfo requisicaoInfo;

    @Autowired
    private PagamentoRepository repository;

    public Pagamento buscarAutenticada(Integer id){
        return repository.findByEmpresaAndId(requisicaoInfo.getEmpresa(), id).orElse(null);
    }

    public Pagamento buscarAutenticada(String codigoIdentificacao){
        return repository.findByEmpresaAndCodigoIdentificacao(requisicaoInfo.getEmpresa(), codigoIdentificacao).orElse(null);
    }

    public Pagamento buscarPublica(Integer id){
        return repository.findById(id).orElse(null);
    }

    public Pagamento buscarPublica(String codigoIdentificacao){
        return repository.findByCodigoIdentificacao(codigoIdentificacao).orElse(null);
    }

    public List<Pagamento> listar(Filtros filtros){
        String dataInicio = filtros.getStringData(DATA_INICIO);
        String dataFim = filtros.getStringData(DATA_FIM);
        Integer empresa = requisicaoInfo.getEmpresa();
        Integer sacado = filtros.getInt(SACADO);
        PagamentoStatus status = filtros.getEnum(STATUS, PagamentoStatus.class);
        return repository.listar(empresa,sacado,status, dataInicio, dataFim);
    }

    public List<Pagamento> listar(Integer cobranca){
        return repository.listar(requisicaoInfo.getEmpresa(), cobranca);
    }

}
