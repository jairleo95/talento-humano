/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.persistence.dao_imp;

import java.util.List;
import com.app.domain.model.ProcessDetail;

/**
 *
 * @author ALPHA
 */
public interface InterfaceDetalleReqProcesoDAO {

    public String insertDetalleReqProceso(ProcessDetail detalleReqProceso);

    public Boolean deleteDetalleReqProceso(String id);

    public Boolean updateDetalleReqProceso(ProcessDetail detalleReqProceso);

    public List<ProcessDetail> listDetalleReqProceso();
}
