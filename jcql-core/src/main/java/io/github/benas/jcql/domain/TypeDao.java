/**
 * The MIT License
 *
 *   Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 */
package io.github.benas.jcql.domain;

import io.github.benas.jcql.model.Type;
import org.springframework.jdbc.core.JdbcTemplate;

public class TypeDao extends BaseDao {

    public TypeDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    public void save(Type type) {
        jdbcTemplate.update("insert into TYPE values (?,?,?,?,?,?,?,?,?,?,?)", type.getId(), type.getName(),
                toSqliteBoolean(type.isPublic()),
                toSqliteBoolean(type.isStatic()),
                toSqliteBoolean(type.isFinal()),
                toSqliteBoolean(type.isAbstract()),
                toSqliteBoolean(type.isClass()),
                toSqliteBoolean(type.isInterface()),
                toSqliteBoolean(type.isEnumeration()),
                toSqliteBoolean(type.isAnnotation()),
                type.getCompilationUnitId());
    }

    public int countClass(String name, String packageName) {
        return jdbcTemplate.queryForObject("select count(c.id) from class c, compilation_unit cu where c.cu_id = cu.id and c.name = ? and cu.package = ?", new Object[]{name, packageName}, Integer.class);
    }

    public int getInterfaceId(String name, String packageName) {
        return jdbcTemplate.queryForObject("select i.id from interface i, compilation_unit cu where i.cu_id = cu.id and i.name = ? and cu.package = ?", new Object[]{name, packageName}, Integer.class);
    }

    public int getClassId(String name, String packageName) {
        return jdbcTemplate.queryForObject("select c.id from class c, compilation_unit cu where c.cu_id = cu.id and c.name = ? and cu.package = ?", new Object[]{name, packageName}, Integer.class);
    }

    public boolean existInterface(String name, String packageName) {
        return jdbcTemplate.queryForObject("select count(i.id) from interface i, compilation_unit cu where i.cu_id = cu.id and i.name = ? and cu.package = ?", new Object[]{name, packageName}, Integer.class) == 1;
    }

    public boolean existClass(String name, String packageName) {
        return jdbcTemplate.queryForObject("select count(c.id) from class c, compilation_unit cu where c.cu_id = cu.id and c.name = ? and cu.package = ?", new Object[]{name, packageName}, Integer.class) == 1;
    }

}