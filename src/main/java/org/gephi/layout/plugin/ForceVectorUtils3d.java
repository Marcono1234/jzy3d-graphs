/*
 * Copyright 2008-2010 Gephi Authors : Mathieu Jacomy Website : http://www.gephi.org
 * 
 * This file is part of Gephi.
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2011 Gephi Consortium. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU General Public License
 * Version 3 only ("GPL") or the Common Development and Distribution License("CDDL") (collectively,
 * the "License"). You may not use this file except in compliance with the License. You can obtain a
 * copy of the License at http://gephi.org/about/legal/license-notice/ or /cddl-1.0.txt and
 * /gpl-3.0.txt. See the License for the specific language governing permissions and limitations
 * under the License. When distributing the software, include this License Header Notice in each
 * file and include the License files at /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the
 * following below the License Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * If you wish your version of this file to be governed by only the CDDL or only the GPL Version 3,
 * indicate your decision by adding "[Contributor] elects to include this software in this
 * distribution under the [CDDL or GPL Version 3] license." If you do not indicate a single choice
 * of license, a recipient has the option to distribute your version of this file under either the
 * CDDL, the GPL Version 3 or to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 3 code and therefore, elected the GPL Version 3 license, then the
 * option applies only if the new code is made subject to such option by the copyright holder.
 * 
 * Contributor(s):
 * 
 * Portions Copyrighted 2011 Gephi Consortium.
 */
package org.gephi.layout.plugin;

import org.gephi.graph.api.Node;

/**
 *
 * @author Mathieu Jacomy
 */
public class ForceVectorUtils3d {
  public static void fcBiRepulsor(Node N1, Node N2, double c) {
    double xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds
    double yDist = N1.y() - N2.y();
    double zDist = N1.z() - N2.z();

    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist); // distance tout
                                                                                    // court

    if (dist > 0) {
      double f = repulsion(c, dist);

      ForceVectorNodeLayoutData3d N1L = N1.getLayoutData();
      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N1L.dx += xDist / dist * f;
      N1L.dy += yDist / dist * f;
      N1L.dz += zDist / dist * f;

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    }
  }

  /*
   * public static void fcBiRepulsor_y(Node N1, Node N2, double c, double verticalization) { double
   * xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds double yDist = N1.y() - N2.y();
   * double zDist = N1.z() - N2.z();
   * 
   * double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist); // distance
   * tout court
   * 
   * if (dist > 0) { double f = repulsion(c, dist);
   * 
   * ForceVectorNodeLayoutData3d N1L = N1.getLayoutData(); ForceVectorNodeLayoutData3d N2L =
   * N2.getLayoutData();
   * 
   * N1L.dx += xDist / dist * f; N1L.dy += verticalization * yDist / dist * f; N1L.dz +=
   * verticalization * zDist / dist * f;
   * 
   * N2L.dx -= xDist / dist * f; N2L.dy -= verticalization * yDist / dist * f; N2L.dz -=
   * verticalization * zDist / dist * f; } }
   */

  public static void fcBiRepulsor_noCollide(Node N1, Node N2, double c) {
    double xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds
    double yDist = N1.y() - N2.y();
    double zDist = N1.z() - N2.z();
    double dist =
        (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist) - N1.size() - N2.size(); // distance
                                                                                                  // (from
                                                                                                  // the
                                                                                                  // border
                                                                                                  // of
                                                                                                  // each
                                                                                                  // node)

    if (dist > 0) {
      double f = repulsion(c, dist);

      ForceVectorNodeLayoutData3d N1L = N1.getLayoutData();
      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N1L.dx += xDist / dist * f;
      N1L.dy += yDist / dist * f;
      N1L.dz += zDist / dist * f;

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    } else if (dist != 0) {
      double f = -c; // flat repulsion

      ForceVectorNodeLayoutData3d N1L = N1.getLayoutData();
      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N1L.dx += xDist / dist * f;
      N1L.dy += yDist / dist * f;
      N1L.dz += zDist / dist * f;

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    }
  }

  public static void fcUniRepulsor(Node N1, Node N2, double c) {
    double xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds
    double yDist = N1.y() - N2.y();
    double zDist = N1.z() - N2.z();
    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist); // distance tout
                                                                                    // court

    if (dist > 0) {
      double f = repulsion(c, dist);

      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    }
  }

  public static void fcBiAttractor(Node N1, Node N2, double c) {
    double xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds
    double yDist = N1.y() - N2.y();
    double zDist = N1.z() - N2.z();
    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist); // distance tout
                                                                                    // court

    if (dist > 0) {
      double f = attraction(c, dist);

      ForceVectorNodeLayoutData3d N1L = N1.getLayoutData();
      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N1L.dx += xDist / dist * f;
      N1L.dy += yDist / dist * f;
      N1L.dz += zDist / dist * f;

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    }
  }

  public static void fcBiAttractor_noCollide(Node N1, Node N2, double c) {
    double xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds
    double yDist = N1.y() - N2.y();
    double zDist = N1.z() - N2.z();
    double dist =
        (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist) - N1.size() - N2.size(); // distance
                                                                                                  // (from
                                                                                                  // the
                                                                                                  // border
                                                                                                  // of
                                                                                                  // each
                                                                                                  // node)

    if (dist > 0) {
      double f = attraction(c, dist);

      ForceVectorNodeLayoutData3d N1L = N1.getLayoutData();
      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N1L.dx += xDist / dist * f;
      N1L.dy += yDist / dist * f;
      N1L.dz += zDist / dist * f;

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    }
  }

  public static void fcBiFlatAttractor(Node N1, Node N2, double c) {
    double xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds
    double yDist = N1.y() - N2.y();
    double zDist = N1.z() - N2.z();
    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist); // distance tout
                                                                                    // court

    if (dist > 0) {
      double f = -c;

      ForceVectorNodeLayoutData3d N1L = N1.getLayoutData();
      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N1L.dx += xDist / dist * f;
      N1L.dy += yDist / dist * f;
      N1L.dz += zDist / dist * f;

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    }
  }

  public static void fcUniAttractor(Node N1, Node N2, float c) {
    double xDist = N1.x() - N2.x(); // distance en x entre les deux noeuds
    double yDist = N1.y() - N2.y();
    double zDist = N1.z() - N2.z();
    double dist = (float) Math.sqrt(xDist * xDist + yDist * yDist + zDist * zDist); // distance tout
                                                                                    // court

    if (dist > 0) {
      double f = attraction(c, dist);

      ForceVectorNodeLayoutData3d N2L = N2.getLayoutData();

      N2L.dx -= xDist / dist * f;
      N2L.dy -= yDist / dist * f;
      N2L.dz -= zDist / dist * f;
    }
  }

  protected static double attraction(double c, double dist) {
    return 0.01 * -c * dist;
  }

  protected static double repulsion(double c, double dist) {
    return 0.001 * c / dist;
  }
}
