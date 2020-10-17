import {Project} from 'app/entities/project.entity';
import {ProjectCreateForm} from 'app/components/project/create/project-create.form';
import {FormControl} from '@angular/forms';

/**
 * Represent the form to create project
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
export class ProjectUpdateForm extends ProjectCreateForm {
  /**
   * The project ID
   */
  public projectId!: number;

  /**
   * Constructor
   *
   * @param project The project parameter
   */
  public constructor(project: Project) {
    super();

    if (!project.id || !project || !project.name) {
      throw Error('The project is invalid');
    }

    this.projectId = project.id;

    this.form.addControl('assignatedUsers', new FormControl(null));

    this.form.patchValue({name: project.name});
    this.form.patchValue({description: project.description});
    this.form.patchValue({assignatedUsers: project.assignatedUsers});
  }

  /**
   * Get a updated project from the form
   *
   * @return The created project. NULL if form is invalid
   */
  get project(): Project {
    const project = super.project;

    project.id = this.projectId;
    project.assignatedUsers = this.form.get('assignatedUsers')?.value;

    return project;
  }
}
